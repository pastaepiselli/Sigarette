import { DecimalPipe } from '@angular/common';
import { ChangeDetectionStrategy, Component, computed, input, signal } from '@angular/core';
import { Cigarette } from '../../../../core/catalog/catalog.models';
import { RequestState } from '../../../../core/http/request-state';
import { RequestFeedback } from '../../../../components/request-feedback/request-feedback';

@Component({
  selector: 'app-home-rankings',
  imports: [DecimalPipe, RequestFeedback],
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './home-rankings.html',
  styleUrl: './home-rankings.scss',
})
export class HomeRankings {
  readonly state = input.required<RequestState<readonly Cigarette[]>>();
  protected readonly ranking = signal<'rating' | 'reviews'>('rating');
  protected readonly skeletonItems = [1, 2, 3, 4, 5, 6];
  protected readonly ranked = computed(() => {
    const state = this.state();
    if (state.status !== 'success') return [];
    return state.data
      .filter((item) => item.ratingCount > 0)
      .sort((a, b) => {
        const difference =
          this.ranking() === 'rating'
            ? b.avgRating - a.avgRating || b.ratingCount - a.ratingCount
            : b.ratingCount - a.ratingCount || b.avgRating - a.avgRating;
        return difference || a.name.localeCompare(b.name, 'it') || a.id - b.id;
      })
      .slice(0, 6);
  });
}
