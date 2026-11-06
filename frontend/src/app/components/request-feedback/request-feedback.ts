import { ChangeDetectionStrategy, Component, input, output } from '@angular/core';

@Component({
  selector: 'app-request-feedback',
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './request-feedback.html',
  styleUrl: './request-feedback.scss',
})
export class RequestFeedback {
  readonly title = input.required<string>();
  readonly message = input<string>();
  readonly isError = input(false);
  readonly retryLabel = input<string>();
  readonly retry = output<void>();
}
