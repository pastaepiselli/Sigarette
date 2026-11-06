import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable, timeout } from 'rxjs';
import { API_CONFIG } from '../core/config/api.config';
import { Brand } from '../core/catalog/catalog.models';

@Injectable({ providedIn: 'root' })
export class BrandService {
  private readonly http = inject(HttpClient);
  private readonly config = inject(API_CONFIG);

  getAll(): Observable<readonly Brand[]> {
    return this.http
      .get<readonly Brand[]>(`${this.config.catalogApiUrl}/brand`)
      .pipe(timeout(this.config.requestTimeoutMs));
  }
}
