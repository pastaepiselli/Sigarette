# Sigarette — Frontend

SPA Angular 22.2, standalone e zoneless, con TypeScript e template strict, Signals, routing lazy, HttpClient e SCSS. Lingua e formattazione: italiano. Nessun framework UI o font remoto.

Identità visiva essenziale: fondo scuro neutro, testo chiaro, superfici grigie e bordi semplici. Nessuna texture, slogan o colore decorativo. Titoli condensati limitati al nome del sito e ai placeholder, senza immagini o font da scaricare.

## Avvio

Requisiti: Node.js compatibile con Angular 22 (`^22.22.3`, `^24.15.0` o `>=26`) e npm. Il progetto è stato preparato con Node 24.19.0 e npm 11.17.0.

```sh
cd frontend
npm ci
npm start
```

Aprire **http://localhost:5173**. La porta è già autorizzata da CORS e dal client Keycloak nel repository. Il dev server ascolta soltanto su localhost; usare questo hostname, non `127.0.0.1`, per rispettare le origini consentite.

`npm start` avvia la modalità **API reali**. `CigaretteService` e `BrandService` leggono il catalogo dal backend; i componenti ricevono i dati dalla pagina e non effettuano chiamate HTTP dirette. Anche `npm run start:api` avvia questa modalità.

Per usare i mock senza backend:

```sh
npm run start:mock
```

Il comando mock prepara automaticamente una copia generata dei soli file `brands.json` e `cigarettes.json` in `.mock-data/`, ignorata da Git. Dopo aver modificato le fixture sorgenti, riavviare `npm run start:mock` per aggiornare la copia.

Per avviare il backend seguire `../backend/README.md`. Il catalogo viene inizializzato dagli script SQL già presenti in `backend/sigarette/db/insert/`. Il frontend non avvia né modifica Docker o i microservizi. Con backend assente vengono mostrati errori espliciti; non esiste un fallback automatico ai mock.

## Configurazione API

Gli URL sono definiti in `src/environments/environment.ts` e `environment.development.ts`, e iniettati tramite `API_CONFIG`. `npm start`, `npm run start:api` e `ng serve` selezionano la configurazione development con API reali. Soltanto `npm run start:mock` abilita i mock. La build production punta al backend locale: **prima di un deployment impostare l'URL pubblico del catalogo** e verificare CORS/HTTPS nell'ambiente di destinazione. Gli environment non devono contenere segreti.

La configurazione mock sostituisce soltanto i provider HTTP con un interceptor che instrada i due GET del catalogo verso JSON locali. Componenti, modelli e servizi HTTP restano gli stessi. La build production non include l'interceptor né le fixture. Le risposte di errore dei JSON restano visibili nell'interfaccia.

API integrate:

- `GET http://localhost:8080/cigarette`
- `GET http://localhost:8080/brand`

Una richiesta per endpoint all'apertura della homepage; timeout di 10 secondi, cancellazione all'uscita dalla pagina e retry manuale indipendente. Nessun token richiesto per queste API.

In modalità mock le richieste di rete effettive sono `GET /mock-data/cigarettes.json` e `GET /mock-data/brands.json`. Con il dataset corrente la home mostra 18 sigarette, 13 marche e 22 recensioni aggregate, sei schede e dodici marche in anteprima. Le fixture di utenti, recensioni, commenti e liste non vengono pubblicate o collegate a pagine non ancora implementate.

## Scope attuale

È implementata soltanto la rotta `/`: layout responsive, introduzione, conteggi, anteprima alfabetica di sei sigarette, classifiche per media e numero di recensioni e indice alfabetico di dodici marche. La navigazione usa ancore alle tre sezioni della homepage; le rotte sconosciute tornano alla home. I numeri non disponibili sono rappresentati con `—`, senza confonderli con zero. Le informazioni essenziali sul contesto del sito sono nel footer.

Le classifiche escludono prodotti senza recensioni. A parità di media viene prima il prodotto con più recensioni; a parità di conteggio viene prima la media più alta; ulteriori parità usano nome e ID. Le statistiche del catalogo sono aggiornate dal backend tramite ActiveMQ e possono avere un ritardo rispetto alle recensioni.

I DTO mantengono i nomi reali (`nicotineMG`, `ratingCount`, `logo_url`). Le immagini assenti o non raggiungibili usano placeholder tipografici locali; nessun asset esterno viene inventato. I prezzi sono informativi. I dati tecnici non disponibili non vengono ricostruiti.

Catalogo completo, dettagli, autenticazione, recensioni, commenti, profili e liste non sono implementati. Le schede non espongono link a pagine future e non sono presenti CTA di login non funzionanti.

## Struttura

- `src/app/pages`: pagine raggiunte dal router. Al momento contiene soltanto `home`.
- `src/app/pages/home/components`: componenti specifici della home: `home-hero`, `home-stats`, `home-catalog`, `home-rankings` e `home-brands`.
- `src/app/components`: componenti riutilizzabili nell'intero progetto: `site-header`, `site-footer`, `cigarette-card` e `request-feedback`.
- `src/app/service`: servizi HTTP per le API del backend (`BrandService` e `CigaretteService`).
- `src/app/core`: DTO, configurazione, stato delle richieste e integrazione mock.
- `public`: asset statici pubblici, secondo la struttura Angular CLI.
- `src/styles`: token e regole comuni delle sezioni.
- `src/app/app.*`: shell essenziale che compone header, router outlet e footer.

Ogni componente ha file TypeScript, HTML e SCSS separati quando necessari. La pagina home coordina solo caricamenti, errori e conteggi; passa gli stati ai figli tramite `input()` e riceve gli eventi di retry tramite `output()`. Le sezioni gestiscono localmente anteprime e ordinamenti; non effettuano chiamate HTTP. Il template della pagina contiene soltanto la composizione delle cinque sezioni.

## Verifiche

```sh
npm run build
npm run build:mock
npm test -- --watch=false
```

La build production va in `dist/sigarette/`, quella mock in `dist/sigarette-mock/`. I test verificano caricamento, ordinamenti, anteprima limitata, stati vuoti, errori indipendenti, retry, fallback immagini, cancellazione, timeout e instradamento delle fixture. Non sostituiscono una verifica end-to-end con il backend reale.

Per il controllo manuale:

1. Aprire la home a 390 px e a larghezza desktop; controllare navigazione, griglie e assenza di scroll orizzontale.
2. Con `npm start` e backend attivo, verificare nel pannello Network `http://localhost:8080/cigarette` e `http://localhost:8080/brand`, quindi confrontare conteggi e classifiche con le risposte reali. Con `npm run start:mock`, verificare invece i due JSON locali e l'assenza di richieste al backend.
3. Cambiare ordinamento: non devono partire nuove richieste.
4. In modalità API, con backend fermo, attendere l'errore e usare Riprova dopo il ripristino.
5. Navigare con Tab, verificare focus e link Vai al contenuto; attivare reduced motion nel browser.

Limite noto del backend: `UserService.getCurrentUser()` richiama un `getUserById()` non definito nei sorgenti analizzati. L'eventuale correzione richiede un'attività backend separata; non riguarda le API pubbliche usate dalla homepage.
