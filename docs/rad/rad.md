---
numbersections: true
include-before:
- |
  ```{=latex}
  \tableofcontents
  \newpage{}
  ```
---

# Introduzione

## Scopo del sistema

La catena di supermercati X non può continuare ad ignorare l'esistenza di un mondo costantemente connesso e le grandi disponibilità tecnologiche. Per far fronte alla concorrenza, specie da parte del mondo e-commerce, X ha chiesto un un'app per smartphone a supporto degli acquisti, in grado di permettere ai clienti la lettura del codice a barre dei prodotti presenti sugli scaffali con il proprio smartphone
e di poterne ottenere delle informazioni dettagliate. Inoltre, che sia anche in grado di proporre agli acquirenti nuovi prodotti di interesse tramite un sistema di raccomandazione.  
Con questo sistema, X vuole attrarre nuovi acquirenti grazie ad un servizio semplice e innovativo nel quale l’utente possa conoscere a fondo i prodotti che sta per acquistare ed essere invogliato ad acquistare altri prodotti, aumentando così gli introiti del supermercato.

## Scope del sistema

Il sistema dovrà fornire una serie di funzionalità:

- Permettere il recupero di informazioni sui prodotti del supermercato tramite **scansione del codice a barre**;
- Assistere l’acquirente nella visualizzazione delle informazioni sui prodotti tramite **AR (Augmented-Reality)**;
- Fornire **raccomandazioni su offerte attive** al momento della spesa, in base a ciò che un acquirente ha scansionato durante la spesa corrente;
- Fornire **raccomandazioni di prodotti similari** a quelli precedentemente scansionati da un acquirente;
- Fornire **raccomandazioni in base a profili di acquisto simili**;
- Consentire ai gestori del supermercato l'**inserimento e la modifica** delle informazioni associate ai **prodotti in vendita**;
- Consentire ai gestori del supermercato l'**inserimento e la modifica delle offerte** sui prodotti;
- Dare agli acquirenti la possibilità di **registrarsi e autenticarsi** alla piattaforma;
- Permette la **ricercare esplicitamente dei prodotti** in vendita al supermercato.

Il sistema non dovrà:

- Sostituire alcuna piattaforma di gestione interna già in uso dai gestori del supermercato;
- Vincolare l'acquirente all'uso dell'app per accedere alle promozioni ordinarie.

## Obiettivi e criteri di successo del progetto

Il progetto si pone i seguenti **obiettivi**:

- Migliorare la fidelizzazione degli acquirenti grazie ai sistemi di supporto e di raccomandazione;
- Incrementare le vendite del cliente grazie al sistema di raccomandazione che favorisce la vendita dei prodotti;
- Realizzare il front-end del sistema come app multipiattaforma (almeno Android e iOS), colpendo la maggior parte del mercato degli smartphone;
- Rendere il sistema prodotto usabile per la maggior parte degli acquirenti tipici del cliente.

Il progetto sarà di successo se, oltre agli obiettivi, rispetta anche i seguenti **criteri di successo**:

- Rispettare tutte le consegne;
- Implementare le funzionalità a proprità alta prima della consegna finale;
- Implementare il sistema usando le tecnologie multipiattaforma Angular, Ionic e Cordova.

## Definizioni, acronimi e abbreviazioni

Alcune definizioni, acronomi e abbreviazioni utili per la lettura del documento:

- **Cliente**: committente del sistema, ovvero la catena di supermercati X;
- **Acquirente**: persona che acquista uno o più prodotti in un supermercato, da non confrondersi con "cliente" di cui sopra;
- **Filiale**: sinonimo di sede (del supermercato);
- **Gestore**: persona predisposta alla gestione del sistema informativo delle sedi del supermercato;
- **AR**: Augmented Reality, realtà aumentata;
- **RF**: Requisito Funzionale;
- **NFR**: Requisito Non Funzionale;
- **UC**: Use case, caso d'uso;
- **UCD**: Use case diagram, diagramma dei casi d'uso. 

## Riferimenti

Di seguito i riferimenti ad altri documenti relativi al sistema:

- **Problem Statement**, disponibile al link: https://github.com/EMAD-2019-Accenture/App/blob/master/docs/problem_statement/Problem%20Statement.pdf

## Panoramica

Nella sezione *Sistema corrente* viene presentata la situazione attuale per affrontare le esigenze del cliente.

Nella sezione *Sistema proposto* viene presentato nel dettaglio il sistema, anzitutto con una panoramica generale, per poi passare ad elencare i requisiti funzionali e non funzionali. Successivamente, si presentano i vari modelli del sistema: gli scenari, il modello dei casi d'uso, il modello ad oggetti, il modello dinamico e l'interfaccia utente.

Infine, nella sezione *Glossario* vengono raccolti tutti i termini tecnici usati nei vari modelli del sistema.

# Sistema corrente

Attualmente il cliente dispone di un proprio sistema informativo, con il quale è in grado di inserire o modificare le informazioni dei prodotti in vendita, delle offerte, del magazzino, dei dipendenti, ecc. Il sistema informativo non si interfaccia in alcun modo con gli acquirenti.

# Sistema proposto

## Panoramica
Il sistema è composto da due parti principali: 

- un sottosistema **front-end** fornito tramite un'app per smartphone Android e iOS accessibile direttamente dagli acquirenti;
- un sottosistema **back-end** che si occupa dell'interrogazione della base di dati del supermercato tramite opportuni web service. Tutto il back-end è deployato sulla piattaforma Oracle Cloud.

Gli utenti del sistema sono gli Acquirenti e i Gestori.

## Requisiti funzionali

Di seguito sono elencati i requisiti funzionali del sistema. Ciascuno dispone di un identificatore univoco, di un nome, di una breve descrizione, di una priorità e dell'elenco degli attori partecipanti.

- **RF_1** - **Scannerizzazione barcode**
  - Descrizione: Il sistema permette la scannerizzazione dei codici a barre dei prodotti
  - Attori partecipanti: Acquirente
  - Priorità: Alta

- **RF_2** - **Visualizzazione informazioni prodotti**
  - Descrizione: Il sistema permette la visualizzazione dei risultati della scannerizzazione tramite AR
  - Attori partecipanti: Acquirente
  - Priorità: Alta

- **RF_3** - **Ricezione raccomandazioni basate su offerte**
  - Descrizione: Il sistema invia raccomandazioni a seconda delle offerte attive al momento della scannerizzazione, lasciando decidere l'acquirente se accettarla o meno
  - Attori partecipanti: Acquirente
  - Priorità: Alta

- **RF_4** - **Ricezione raccomandazioni di prodotti simili**
  - Descrizione: Il sistema invia raccomandazioni a seconda della tipologia di prodotto che è stato scanerizzato, lasciando decidere l'acquirente se accettarla o meno
  - Attori partecipanti: Acquirente
  - Priorità: Alta

- **RF_5** - **Registrazione**
  - Descrizione: 
  - Attori partecipanti: Acquirente
  - Priorità: Media

- **RF_6** - **Login**
  - Descrizione: 
  - Attori partecipanti: Acquirente
  - Priorità: Media

- **RF_7** - **Logout**
  - Descrizione: 
  - Attori partecipanti: Acquirente
  - Priorità: Media

- **RF_8** - **Ricezione raccomandazioni basato su profili simili**
  - Descrizione: 
  - Attori partecipanti: Acquirente
  - Priorità: Media

- **RF_9** - **Visualizzazione catalogo prodotti**
  - Descrizione: 
  - Attori partecipanti: Acquirente
  - Priorità: Media

- **RF_10** - **Inserimento di un prodotto**
  - Descrizione: 
  - Attori partecipanti: Gestore
  - Priorità: Media

- **RF_11** - **Modifica di un prodotto**
  - Descrizione: 
  - Attori partecipanti: Gestore
  - Priorità: Media

- **RF_12** - **Rimozione di un prodotto**
  - Descrizione: 
  - Attori partecipanti: Gestore
  - Priorità: Media

- **RF_13** - **Inserimento di un'offerta**
  - Descrizione: 
  - Attori partecipanti: Gestore
  - Priorità: Media

- **RF_14** - **Modifica di un'offerta**
  - Descrizione: 
  - Attori partecipanti: Gestore
  - Priorità: Media

- **RF_15** - **Rimozione di un'offerta**
  - Descrizione: 
  - Attori partecipanti: Gestore
  - Priorità: Media

## Requisiti non funzionali

### Usabilità

### Affidabilità

### Prestazioni

### Supportabilità

### Implementazione

### Interfaccia

### Packaging

### Legali

## Modelli del sistema

### Modello dei casi d'uso

| Nome                   | UC_1 - Barcode Scan                                                                                                                                                                                                                                                                                                                                                                                                                                 |
|------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Attori  Partecipanti   | Acquirente                                                                                                                                                                                                                                                                                                                                                                                                                                          |
| Flusso  di Eventi      | 1. L' Acquirente seleziona la voce "Scansiona Prodotto".<br>&nbsp;&nbsp;2. Il sistema apre la camera per la scansione.<br>3. L'acquirente inquadra il codice a barre del prodotto.<br>&nbsp;&nbsp;4. Il sistema, avendo letto correttamente il codice a barre       risponde con una serie di informazioni relative al prodotto       (codice, categoria, stato, marchio, scadenza,peso lordo,       peso netto, durata garanzia, stagionalità, promozione, prezzo promozione) |
| Condizioni di Ingresso | L' Acquirente ha avviato il sistema                                                                                                                                                                                                                                                                                                                                                                                                                 |
| Condizioni d'uscita    | L'Acquirente visualizza correttamente le informazioni sul prodotto.                                                                                                                                                                                                                                                                                                                                                                                 |
| Flussi alternativi.    | Al punto 4. Il sistema potrebbe rispondere con un messaggio d'errore dovuto ad errori di rete o all'assenza del prodotto nel DB.                                                                                                                                                                                                                                                                                                                    |

### Modello ad oggetti

### Interfaccia utente - navigational path e mock-up

# Glossario