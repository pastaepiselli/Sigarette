import { registerLocaleData } from '@angular/common';
import localeIt from '@angular/common/locales/it';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LOCALE_ID } from '@angular/core';
import { provideRouter } from '@angular/router';
import { afterEach, beforeEach, describe, expect, it, vi } from 'vitest';
import { Brand, Cigarette } from '../../core/catalog/catalog.models';
import { API_CONFIG } from '../../core/config/api.config';
import { httpProviders } from '../../core/http/http.providers';
import { HomePage } from './home-page';

registerLocaleData(localeIt);

// Dati sintetici esclusivamente per verificare il contratto HTTP e gli stati della UI.
const brand: Brand = {
  id: 1,
  name: 'Marca di test',
  country: null,
  description: null,
  logo_url: null,
};
function cigarette(id: number, name: string, avgRating: number, ratingCount: number): Cigarette {
  return {
    id,
    name,
    avgRating,
    ratingCount,
    brand,
    price: 6.5,
    description: null,
    imageUrl: null,
    nicotineMG: null,
    tar: null,
    co: null,
    format: null,
    sticksPerPack: null,
    isMenthol: false,
    createdAt: '2026-06-05T12:00:00',
  };
}

describe('Homepage: API pubbliche e comportamento', () => {
  let fixture: ComponentFixture<HomePage>;
  let http: HttpTestingController;
  let element: HTMLElement;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HomePage],
      providers: [
        provideRouter([]),
        httpProviders,
        provideHttpClientTesting(),
        { provide: LOCALE_ID, useValue: 'it-IT' },
        {
          provide: API_CONFIG,
          useValue: { catalogApiUrl: '/api-catalog-test', requestTimeoutMs: 10_000 },
        },
      ],
    }).compileComponents();
    http = TestBed.inject(HttpTestingController);
    fixture = TestBed.createComponent(HomePage);
    element = fixture.nativeElement;
    fixture.detectChanges();
  });

  afterEach(() => {
    http.verify();
    vi.useRealTimers();
  });

  function respond(products: readonly Cigarette[], brands: readonly Brand[] = [brand]): void {
    http.expectOne('/api-catalog-test/cigarette').flush(products);
    http.expectOne('/api-catalog-test/brand').flush(brands);
    fixture.detectChanges();
  }

  function click(selector: string): void {
    const button = element.querySelector<HTMLButtonElement>(selector);
    expect(button).not.toBeNull();
    button?.click();
    fixture.detectChanges();
  }

  it('usa i provider API reali, carica una sola volta ciascun endpoint e mostra gli skeleton', () => {
    expect(element.querySelector('#esplora')?.getAttribute('aria-busy')).toBe('true');
    expect(element.querySelectorAll('.skeleton-cover')).toHaveLength(6);
    const request = http.expectOne('/api-catalog-test/cigarette');
    expect(request.request.method).toBe('GET');
    expect(request.request.headers.has('Authorization')).toBe(false);
    request.flush([]);
    http.expectOne('/api-catalog-test/brand').flush([]);
  });

  it('ordina le medie, esclude i prodotti senza recensioni e cambia classifica senza nuove richieste', () => {
    respond([
      cigarette(1, 'Più recensita', 4.2, 20),
      cigarette(2, 'Media più alta', 4.8, 2),
      cigarette(3, 'Senza voti', 0, 0),
    ]);
    const names = () =>
      [...element.querySelectorAll('.rank-product h3')].map((item) => item.textContent?.trim());
    expect(names()).toEqual(['Media più alta', 'Più recensita']);
    expect(element.querySelector('.rank-score')?.textContent).toContain('4,8');
    click('.ranking-controls button:last-child');
    expect(names()).toEqual(['Più recensita', 'Media più alta']);
    expect(
      element.querySelector('.ranking-controls button:last-child')?.getAttribute('aria-pressed'),
    ).toBe('true');
    expect(element.querySelectorAll('.archive-stats dd')[2]?.textContent?.trim()).toBe('22');
    http.expectNone('/api-catalog-test/cigarette');
  });

  it('limita l’anteprima a sei prodotti, senza trasformarla nella pagina catalogo', () => {
    respond(
      Array.from({ length: 8 }, (_, index) => cigarette(index + 1, `Prodotto ${8 - index}`, 0, 0)),
    );
    const cards = element.querySelectorAll('app-cigarette-card');
    expect(cards).toHaveLength(6);
    expect(cards[0]?.querySelector('h3')?.textContent).toBe('Prodotto 1');
    expect(element.querySelector('.section-footnote')?.textContent).toContain('6 voci su 8');
    expect(element.querySelector('a[href^="/cigarettes"]')).toBeNull();
  });

  it('distingue un archivio vuoto da una classifica senza voti', () => {
    respond([], []);
    expect(element.querySelector('#esplora')?.textContent).toContain(
      'Nessuna sigaretta in catalogo',
    );
    expect(element.querySelector('#classifiche')?.textContent).toContain(
      'Nessuna valutazione disponibile',
    );
    expect(element.querySelector('#marche')?.textContent).toContain('Nessuna marca disponibile');
    expect(element.querySelectorAll('.archive-stats dd')[0]?.textContent?.trim()).toBe('0');
  });

  it('mantiene le marche disponibili se il catalogo fallisce e riprova solo la richiesta fallita', () => {
    http.expectOne('/api-catalog-test/cigarette').error(new ProgressEvent('error'));
    http.expectOne('/api-catalog-test/brand').flush([brand]);
    fixture.detectChanges();
    expect(element.querySelector('#esplora [role="alert"]')?.textContent).toContain(
      'Non riusciamo a contattare',
    );
    expect(element.querySelector('.brand-name')?.textContent).toBe(brand.name);
    expect(element.querySelectorAll('.archive-stats dd')[0]?.textContent?.trim()).toBe('—');
    click('#esplora button');
    http.expectNone('/api-catalog-test/brand');
    http.expectOne('/api-catalog-test/cigarette').flush([cigarette(1, 'Ripristinato', 4, 1)]);
    fixture.detectChanges();
    expect(element.querySelector('#esplora [role="alert"]')).toBeNull();
    expect(element.querySelector('app-cigarette-card h3')?.textContent).toBe('Ripristinato');
  });

  it('mantiene il catalogo disponibile se fallisce il caricamento delle marche', () => {
    http.expectOne('/api-catalog-test/cigarette').flush([cigarette(1, 'Disponibile', 0, 0)]);
    http
      .expectOne('/api-catalog-test/brand')
      .flush({}, { status: 500, statusText: 'Server Error' });
    fixture.detectChanges();
    expect(element.querySelector('app-cigarette-card h3')?.textContent).toBe('Disponibile');
    expect(element.querySelector('#marche [role="alert"]')).not.toBeNull();
    click('#marche button');
    http.expectOne('/api-catalog-test/brand').flush([brand]);
    fixture.detectChanges();
    expect(element.querySelector('#marche [role="alert"]')).toBeNull();
  });

  it('usa il placeholder locale per URL immagine assente o non raggiungibile', () => {
    respond([
      { ...cigarette(1, 'Immagine non disponibile', 0, 0), imageUrl: '/immagine-di-test.png' },
    ]);
    const image = element.querySelector('app-cigarette-card img');
    expect(image).not.toBeNull();
    image?.dispatchEvent(new Event('error'));
    fixture.detectChanges();
    expect(element.querySelector('app-cigarette-card img')).toBeNull();
    expect(element.querySelector('.cover-placeholder')).not.toBeNull();
    expect(element.querySelector('app-cigarette-card')?.textContent).toContain(
      'Immagine non disponibile.',
    );
  });

  it('cancella le richieste ancora attive quando si lascia la pagina', () => {
    const catalogRequest = http.expectOne('/api-catalog-test/cigarette');
    const brandsRequest = http.expectOne('/api-catalog-test/brand');
    fixture.destroy();
    expect(catalogRequest.cancelled).toBe(true);
    expect(brandsRequest.cancelled).toBe(true);
  });

  it('mostra un errore esplicito dopo il timeout e termina il caricamento', async () => {
    vi.useFakeTimers();
    // Le richieste sono già state avviate: questa verifica usa un nuovo caricamento.
    http.expectOne('/api-catalog-test/cigarette').error(new ProgressEvent('error'));
    http.expectOne('/api-catalog-test/brand').flush([]);
    fixture.detectChanges();
    click('#esplora button');
    const retryRequest = http.expectOne('/api-catalog-test/cigarette');
    await vi.advanceTimersByTimeAsync(10_001);
    fixture.detectChanges();
    expect(retryRequest.cancelled).toBe(true);
    expect(element.querySelector('#esplora [role="alert"]')?.textContent).toContain('troppo tempo');
    expect(element.querySelector('#esplora')?.getAttribute('aria-busy')).toBe('false');
  });
});
