import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { API_CONFIG } from '../config/api.config';

/** Attivo solo nella build mock: i servizi conservano il contratto delle API reali. */
export const mockCatalogInterceptor: HttpInterceptorFn = (request, next) => {
  const config = inject(API_CONFIG);
  if (request.method !== 'GET') return next(request);

  const fixtures: Readonly<Record<string, string>> = {
    [`${config.catalogApiUrl}/cigarette`]: '/mock-data/cigarettes.json',
    [`${config.catalogApiUrl}/brand`]: '/mock-data/brands.json',
  };
  const fixtureUrl = fixtures[request.url];
  return next(fixtureUrl ? request.clone({ url: fixtureUrl }) : request);
};
