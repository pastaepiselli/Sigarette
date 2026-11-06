import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { mockCatalogInterceptor } from '../mock/mock-catalog.interceptor';

export const httpProviders = provideHttpClient(withInterceptors([mockCatalogInterceptor]));
