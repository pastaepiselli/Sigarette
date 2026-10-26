# Backend — CigBase

Il backend gestisce il catalogo di sigarette italiane, le recensioni con voto e testo, i commenti, i profili applicativi degli utenti e le loro liste personali. Il catalogo è stato compilato a partire dal [documento ADM sulle sigarette](https://www.adm.gov.it/portale/documents/20182/1106899/3.1-LIST+SIGARETTE.pdf/42c96880-1905-fbb1-7d19-caf6a7bcc57a?t=1780641495560), con prezzi aggiornati al 05/06/2026.

## Tecnologie

- **Java 21** e **Spring Boot** per i servizi backend (le versioni di Spring Boot sono definite separatamente nei rispettivi `pom.xml`).
- **PostgreSQL 17** per i dati, suddivisi in tre database: `sigarette`, `reviews` e `users`.
- **ActiveMQ Classic** per la messaggistica tra il servizio recensioni e il catalogo.
- **Keycloak** per la gestione degli utenti e l’autenticazione tramite token JWT.
- **Docker Compose** per avviare l’ambiente locale; **Springdoc OpenAPI** per la documentazione delle API.

## Architettura
Il backend è composto da tre microservizi. Ogni servizio possiede il proprio database logico PostgreSQL.

| Servizio | Porta | Responsabilità |
|---|---:|---|
| `sigarette` | 8080 | Catalogo di sigarette e marche; conserva anche valutazione media e numero di recensioni. |
| `review-service` | 8081 | Recensioni e commenti. Emette eventi quando una recensione viene creata, modificata o cancellata. |
| `user-service` | 8083 | Profilo applicativo dell’utente e liste di sigarette salvate. |
| Keycloak | 8060 | Autenticazione e rilascio dei token per il realm `sigarette`. |
| ActiveMQ | 61616 | Broker JMS per gli eventi sulle recensioni; la console web è sulla porta 8161. |
| PostgreSQL | 5432 | Istanza condivisa con database separati per i tre servizi. |

Il servizio recensioni pubblica su ActiveMQ gli eventi di creazione, modifica e cancellazione, includendo il voto nuovo (e, in caso di modifica, quello precedente). Il servizio catalogo li usa per aggiornare la valutazione media e il conteggio delle valutazioni della sigaretta interessata. (gasa una cifra)

![Diagramma dell'architettura backend](../architettura.png)

## Avvio locale

È necessario avere Docker e Docker Compose installati. Dalla cartella `backend/` eseguire:

```bash
docker compose up --build
```

Al primo avvio gli script SQL inizializzano i database e caricano il catalogo.

Le credenziali iniziali predefinite per le console Keycloak e ActiveMQ sono `admin` / `admin`. Per lo sviluppo locale, Compose consente di sostituire le credenziali tramite le variabili `KEYCLOAK_ADMIN`, `KEYCLOAK_ADMIN_PASSWORD`, `POSTGRES_USER` e `POSTGRES_PASSWORD`.

Per arrestare i container mantenendo i dati:

```bash
docker compose down
```

Per eliminare anche il volume PostgreSQL e ricreare i database al prossimo avvio:

```bash
docker compose down -v
```

## API REST

Le API restituiscono e accettano JSON. Il catalogo non richiede autenticazione; le API dei servizi recensioni e utenti richiedono un token Bearer JWT valido emesso da Keycloak. Le pagine Swagger e gli endpoint OpenAPI sono pubblici.

### Catalogo (`:8080`)

| Metodo | Percorso | Descrizione |
|---|---|---|
| `GET` | `/cigarette` | Elenco delle sigarette. |
| `GET` | `/cigarette/{id}` | Dettaglio di una sigaretta. |
| `GET` | `/brand` | Elenco delle marche. |
| `GET` | `/brand/{id}` | Dettaglio di una marca. |

### Recensioni e commenti (`:8081`)

| Metodo | Percorso | Descrizione |
|---|---|---|
| `GET` | `/reviews/{id}` | Dettaglio di una recensione. |
| `GET` | `/reviews/cigarette/{idCigarette}` | Recensioni associate a una sigaretta. |
| `POST` | `/reviews` | Crea una recensione per l’utente autenticato. |
| `PUT` | `/reviews/{idReview}` | Modifica una recensione. |
| `DELETE` | `/reviews/{idReview}` | Elimina una recensione. |
| `GET` | `/reviews/{idReview}/comments` | Elenco dei commenti di una recensione. |
| `GET` | `/reviews/{idReview}/comments/{id}` | Dettaglio di un commento. |
| `POST` | `/reviews/{idReview}/comments` | Aggiunge un commento. |
| `PUT` | `/reviews/{idReview}/comments/{idComment}` | Modifica un commento. |
| `DELETE` | `/reviews/{idReview}/comments/{id}` | Elimina un commento. |

Il corpo di una richiesta per creare o aggiornare una recensione ha questa forma:

```json
{
  "idCigarette": 1,
  "rating": 4.5,
  "description": "Descrizione della recensione"
}
```

Per creare o aggiornare un commento, il corpo è `{ "description": "Testo del commento" }`.

### Utenti e liste (`:8083`)

| Metodo | Percorso | Descrizione |
|---|---|---|
| `POST` | `/users/create` | Crea il profilo applicativo dell’utente autenticato. |
| `GET` | `/users/me` | Legge il proprio profilo. |
| `PUT` | `/users/me` | Aggiorna il proprio profilo. |
| `GET` | `/users/{uuid}` | Legge un profilo tramite UUID. |
| `GET` | `/users/me/saved-cigarettes?status={status}` | Elenca le proprie sigarette salvate per stato. |
| `GET` | `/users/{username}/saved-cigarettes?status={status}` | Elenca le sigarette salvate da un utente per stato. |
| `POST` | `/users/me/saved-cigarettes` | Aggiunge una sigaretta a una lista personale. |
| `DELETE` | `/users/me/saved-cigarettes/{idSavedCig}` | Rimuove una sigaretta salvata. |

Il corpo per salvare una sigaretta è `{ "idCigarette": 1, "status": "WANT_TO_TRY" }`. I valori ammessi per `status` sono `WANT_TO_TRY` (vorrei provare) e `LIKED` (preferite).

## Swagger UI

La documentazione OpenAPI dei singoli servizi è disponibile agli indirizzi:

- <http://localhost:8080/swagger-ui/index.html>
- <http://localhost:8081/swagger-ui/index.html>
- <http://localhost:8083/swagger-ui/index.html>

## Struttura del backend

I progetti Spring Boot si trovano in `backend/`:

```text
backend/
├── sigarette/       # Catalogo e marche
├── review-service/  # Recensioni e commenti
├── user-service/    # Profili e liste personali
├── database/        # Inizializzazione PostgreSQL
└── keycloak/        # Configurazione del realm
```

Il file `docker-compose.yaml` di questa cartella include il Compose principale nella radice del repository. In alternativa, dalla radice si può eseguire lo stesso comando `docker compose up --build`.
