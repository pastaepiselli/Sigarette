import { DecimalPipe } from '@angular/common';
import { ChangeDetectionStrategy, Component, computed, input, output } from '@angular/core';
import { Brand } from '../../../../core/catalog/catalog.models';
import { RequestState } from '../../../../core/http/request-state';
import { RequestFeedback } from '../../../../components/request-feedback/request-feedback';

@Component({
  selector: 'app-home-brands',
  imports: [DecimalPipe, RequestFeedback],
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './home-brands.html',
  styleUrl: './home-brands.scss',
})
export class HomeBrands {
  readonly state = input.required<RequestState<readonly Brand[]>>();
  readonly retry = output<void>();
  protected readonly skeletonItems = [1, 2, 3, 4, 5, 6];
  protected readonly preview = computed(() => {
    const state = this.state();
    return state.status === 'success'
      ? [...state.data]
          .sort((a, b) => a.name.localeCompare(b.name, 'it') || a.id - b.id)
          .slice(0, 12)
      : [];
  });
}
