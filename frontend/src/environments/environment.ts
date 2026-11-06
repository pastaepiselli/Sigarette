// Configurazione locale di default. Impostare l'URL pubblico del catalogo prima del deployment.
export const environment = {
  catalogApiUrl: 'http://localhost:8080',
  requestTimeoutMs: 10_000,
} as const;
