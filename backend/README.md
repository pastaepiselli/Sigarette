# Sigarette backend
Nel ms sigarette sono salvate tutte le sigarette italiane che ho trovato sul documento [adm-tabacchi](https://www.adm.gov.it/portale/documents/20182/1106899/3.1-LIST+SIGARETTE.pdf/42c96880-1905-fbb1-7d19-caf6a7bcc57a?t=1780641495560) (prezzi aggiornati fino al 05/06/2026.
Backend che permette la registazione di un utente tramite keycloak, salva le sue info (bio, propic...) in user-service, recensione di sigarette con voto e recensione scritta.
Possibilita per ogni utente di avere 2 tipi di liste:
- vorrei provare
- preferite

# Avviare il backend
Per avviare database, activemq, Keycloak e tutti i microservizi, vai nella cartella `/backend` ed esegui:

```bash
docker compose up --build
```

Per istruzioni, porte e credenziali locali vedi la sezione Docker nel [README principale](../README.md).


