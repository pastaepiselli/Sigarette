# Sigarette backend
Nel ms sigarette sono salvate tutte le sigarette italiane che ho trovato sul documento [adm-tabacchi](https://www.adm.gov.it/portale/documents/20182/1106899/3.1-LIST+SIGARETTE.pdf/42c96880-1905-fbb1-7d19-caf6a7bcc57a?t=1780641495560) (prezzi aggiornati fino al 05/06/2026.
Backend che permette la registazione di un utente tramite keycloak, salva le sue info (bio, propic...) in user-service, recensione di sigarette con voto e recensione scritta.
Possibilita per ogni utente di avere 2 tipi di liste:
- vorrei provare
- preferite

# Avviare il backend
Per avviare database, activemq, Keycloak e tutti i microservizi, vai nella cartella `/backend` ed esegui:

## Architettura Backend

![architettura backend](architettura.png)

```bash
docker compose up --build
```
Al primo avvio il database viene creato e inizializato con gli script SQL, incluso il catalogo delle sigarette. Gli URL locali sono:

- Catalogo sigarette: `http://localhost:8080`
- Recensioni: `http://localhost:8081`
- Utenti e liste salvate: `http://localhost:8083`
- Keycloak: `http://localhost:8060` (realm `sigarette`) (credenziali `admin` / `admin`)
- ActiveMQ console: `http://localhost:8161` (credenziali predefinite `admin` / `admin`)
- Swagger sigarette: `http://localhost:8080/swagger-ui/index.html`
- Swagger recensioni: `http://localhost:8081/swagger-ui/index.html`
- Swagger utenti: `http://localhost:8083/swagger-ui/index.html`

Per fermare i servizi mantenendo i dati, eseguire `docker compose down`. Per ricreare da zero anche i database, eseguire `docker compose down -v` prima di riavviare.





