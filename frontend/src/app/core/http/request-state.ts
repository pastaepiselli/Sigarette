import { HttpErrorResponse } from '@angular/common/http';
import { TimeoutError } from 'rxjs';

export type RequestState<T> =
  | { readonly status: 'idle' | 'loading' }
  | { readonly status: 'success'; readonly data: T }
  | { readonly status: 'error'; readonly message: string };

export function requestErrorMessage(error: unknown): string {
  if (error instanceof TimeoutError) {
    return 'Il server sta impiegando troppo tempo a rispondere. Riprova tra poco.';
  }
  if (error instanceof HttpErrorResponse) {
    switch (error.status) {
      case 0:
        return 'Non riusciamo a contattare il catalogo. Controlla la connessione e riprova.';
      case 401:
        return 'Il server richiede un accesso per questa risorsa.';
      case 403:
        return 'Il server non consente di consultare questa risorsa.';
      case 404:
        return 'La risorsa richiesta non è disponibile.';
      case 429:
        return 'Troppe richieste in poco tempo. Attendi e riprova.';
    }
  }
  return 'Non è stato possibile caricare i dati. Riprova tra poco.';
}
