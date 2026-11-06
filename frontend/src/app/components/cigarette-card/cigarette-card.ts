import { CurrencyPipe, DecimalPipe } from '@angular/common';
import { ChangeDetectionStrategy, Component, computed, input, signal } from '@angular/core';
import { Cigarette } from '../../core/catalog/catalog.models';

@Component({
  selector: 'app-cigarette-card',
  imports: [CurrencyPipe, DecimalPipe],
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './cigarette-card.html',
  styleUrl: './cigarette-card.scss',
})
export class CigaretteCard {
  readonly cigarette = input.required<Cigarette>();
  protected readonly failedImageUrl = signal<string | null>(null);
  protected readonly imageUrl = computed(() => {
    const url = this.cigarette().imageUrl;
    return url && url !== this.failedImageUrl() ? url : null;
  });
}
