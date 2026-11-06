import { DecimalPipe } from '@angular/common';
import { ChangeDetectionStrategy, Component, input } from '@angular/core';

@Component({
  selector: 'app-home-stats',
  imports: [DecimalPipe],
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './home-stats.html',
  styleUrl: './home-stats.scss',
})
export class HomeStats {
  readonly cigaretteCount = input.required<number | null>();
  readonly brandCount = input.required<number | null>();
  readonly reviewCount = input.required<number | null>();
}
