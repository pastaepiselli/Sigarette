import { provideHttpClient, withInterceptors, HttpClient } from '@angular/common/http';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { API_CONFIG } from '../config/api.config';
import { BrandService } from '../../service/brand.service';
import { CigaretteService } from '../../service/cigarette.service';
import { mockCatalogInterceptor } from './mock-catalog.interceptor';

describe('Modalità mock del catalogo', () => {
  let http: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(withInterceptors([mockCatalogInterceptor])),
        provideHttpClientTesting(),
        {
          provide: API_CONFIG,
          useValue: { catalogApiUrl: 'http://localhost:8080', requestTimeoutMs: 10_000 },
        },
      ],
    });
    http = TestBed.inject(HttpTestingController);
  });

  afterEach(() => http.verify());

  it('collega entrambi i servizi alle fixture locali senza contattare il backend', () => {
    TestBed.inject(CigaretteService)
      .getAll()
      .subscribe((items) => expect(items).toEqual([]));
    TestBed.inject(BrandService)
      .getAll()
      .subscribe((items) => expect(items).toEqual([]));
    http.expectOne('/mock-data/cigarettes.json').flush([]);
    http.expectOne('/mock-data/brands.json').flush([]);
    http.expectNone('http://localhost:8080/cigarette');
    http.expectNone('http://localhost:8080/brand');
  });

  it('propaga gli errori di caricamento delle fixture', () => {
    TestBed.inject(CigaretteService)
      .getAll()
      .subscribe({
        next: () => {
          throw new Error('La richiesta doveva fallire');
        },
        error: (error: unknown) => expect(error).toMatchObject({ status: 404 }),
      });
    http
      .expectOne('/mock-data/cigarettes.json')
      .flush(null, { status: 404, statusText: 'Not Found' });
  });

  it('non intercetta scritture o richieste estranee al catalogo', () => {
    const client = TestBed.inject(HttpClient);
    client.post('http://localhost:8080/cigarette', {}).subscribe();
    client.get('http://localhost:8083/users/me').subscribe();
    client.get('https://example.test/brand').subscribe();
    http.expectOne('http://localhost:8080/cigarette').flush({});
    http.expectOne('http://localhost:8083/users/me').flush({});
    http.expectOne('https://example.test/brand').flush([]);
  });
});
