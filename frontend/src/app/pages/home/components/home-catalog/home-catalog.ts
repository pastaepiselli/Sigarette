import { ChangeDetectionStrategy, Component, computed, input, output } from '@angular/core';
import { DecimalPipe } from '@angular/common';
import { Cigarette } from '../../../../core/catalog/catalog.models';
import { RequestState } from '../../../../core/http/request-state';
import { CigaretteCard } from '../../../../components/cigarette-card/cigarette-card';
import { RequestFeedback } from '../../../../components/request-feedback/request-feedback';

@Component({
  selector: 'app-home-catalog',
  imports: [DecimalPipe, CigaretteCard, RequestFeedback],
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './home-catalog.html',
  styleUrl: './home-catalog.scss',
})
export class HomeCatalog {
  readonly state = input.required<RequestState<readonly Cigarette[]>>();
  readonly retry = output<void>();
  protected readonly skeletonItems = [1, 2, 3, 4, 5, 6];
  protected readonly preview = computed(() => {
    const state = this.state();
    return state.status === 'success'
      ? [...state.data]
          .sort((a, b) => a.name.localeCompare(b.name, 'it') || a.id - b.id)
          .slice(0, 6)
      : [];
  });
}
