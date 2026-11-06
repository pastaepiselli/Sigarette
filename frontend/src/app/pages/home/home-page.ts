import {
  ChangeDetectionStrategy,
  Component,
  computed,
  DestroyRef,
  inject,
  OnInit,
  signal,
} from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { BrandService } from '../../service/brand.service';
import { CigaretteService } from '../../service/cigarette.service';
import { Brand, Cigarette } from '../../core/catalog/catalog.models';
import { RequestState, requestErrorMessage } from '../../core/http/request-state';
import { HomeHero } from './components/home-hero/home-hero';
import { HomeStats } from './components/home-stats/home-stats';
import { HomeCatalog } from './components/home-catalog/home-catalog';
import { HomeRankings } from './components/home-rankings/home-rankings';
import { HomeBrands } from './components/home-brands/home-brands';

@Component({
  selector: 'app-home-page',
  imports: [HomeHero, HomeStats, HomeCatalog, HomeRankings, HomeBrands],
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './home-page.html',
  styleUrl: './home-page.scss',
})
export class HomePage implements OnInit {
  private readonly cigaretteService = inject(CigaretteService);
  private readonly brandService = inject(BrandService);
  private readonly destroyRef = inject(DestroyRef);

  protected readonly catalogState = signal<RequestState<readonly Cigarette[]>>({ status: 'idle' });
  protected readonly brandsState = signal<RequestState<readonly Brand[]>>({ status: 'idle' });
  protected readonly cigaretteCount = computed(() => {
    const state = this.catalogState();
    return state.status === 'success' ? state.data.length : null;
  });
  protected readonly brandCount = computed(() => {
    const state = this.brandsState();
    return state.status === 'success' ? state.data.length : null;
  });
  protected readonly reviewCount = computed(() => {
    const state = this.catalogState();
    return state.status === 'success'
      ? state.data.reduce((total, item) => total + item.ratingCount, 0)
      : null;
  });

  ngOnInit(): void {
    this.loadCatalog();
    this.loadBrands();
  }

  protected loadCatalog(): void {
    if (this.catalogState().status === 'loading') return;
    this.catalogState.set({ status: 'loading' });
    this.cigaretteService
      .getAll()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (data) => this.catalogState.set({ status: 'success', data }),
        error: (error: unknown) =>
          this.catalogState.set({ status: 'error', message: requestErrorMessage(error) }),
      });
  }

  protected loadBrands(): void {
    if (this.brandsState().status === 'loading') return;
    this.brandsState.set({ status: 'loading' });
    this.brandService
      .getAll()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (data) => this.brandsState.set({ status: 'success', data }),
        error: (error: unknown) =>
          this.brandsState.set({ status: 'error', message: requestErrorMessage(error) }),
      });
  }
}
