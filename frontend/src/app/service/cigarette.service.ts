import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable, timeout } from 'rxjs';
import { API_CONFIG } from '../core/config/api.config';
import { Cigarette } from '../core/catalog/catalog.models';

@Injectable({ providedIn: 'root' })
export class CigaretteService {
  private readonly http = inject(HttpClient);
  private readonly config = inject(API_CONFIG);

  getAll(): Observable<readonly Cigarette[]> {
    return this.http
      .get<readonly Cigarette[]>(`${this.config.catalogApiUrl}/cigarette`)
      .pipe(timeout(this.config.requestTimeoutMs));
  }
}
