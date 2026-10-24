# Sigarette progetto

## Architettura Backend

![architettura backend](architettura.png)

## Avvio locale con Docker

Prerequisiti: Docker con Docker Compose.

Dalla root del repository:

```bash
docker compose up --build
```

Al primo avvio il database viene creato e inizializato con gli script SQL, incluso il catalogo delle sigarette. Gli URL locali sono:

- Catalogo sigarette: `http://localhost:8080`
- Recensioni: `http://localhost:8081`
- Utenti e liste salvate: `http://localhost:8083`
- Keycloak: `http://localhost:8060` (realm `sigarette`)
- ActiveMQ console: `http://localhost:8161` (credenziali predefinite `admin` / `admin`)
- Swagger sigarette: `http://localhost:8080/swagger-ui/index.html`
- Swagger recensioni: `http://localhost:8081/swagger-ui/index.html`
- Swagger utenti: `http://localhost:8083/swagger-ui/index.html`

Per fermare i servizi mantenendo i dati, eseguire `docker compose down`. Per ricreare da zero anche i database, eseguire `docker compose down -v` prima di riavviare.

