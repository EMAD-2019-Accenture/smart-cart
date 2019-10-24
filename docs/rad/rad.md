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

La catena di supermercati Coop non può continuare ad ignorare l'esistenza di un mondo costantemente connesso e le grandi disponibilità tecnologiche. Per far fronte alla concorrenza, specie da parte del mondo e-commerce, Coop ha chiesto un'app per smartphone a supporto degli acquisti, in grado di permettere agli acquirenti la lettura del codice a barre degli articoli presenti sugli scaffali con il proprio smartphone e di poterne ottenere delle informazioni dettagliate. Inoltre, che sia anche in grado di proporre agli acquirenti nuovi articoli di interesse tramite un sistema di raccomandazione.  
Con questo sistema, Coop vuole attrarre nuovi acquirenti grazie ad un servizio semplice e innovativo nel quale l’utente possa conoscere a fondo gli articoli che sta per acquistare ed essere invogliato ad acquistarne altri, aumentando così gli introiti del supermercato e migliorando l'esperienza degli acquirenti.

## Scope del sistema

Il sistema dovrà fornire una serie di funzionalità:

- Permettere il **recupero di informazioni** sugli articoli del supermercato tramite **scansione del codice a barre**;
- Permettere all'acquirente di **aggiungere**, **eliminare**, gestire la **numerosità** e il **costo totale** degli articoli scansionati;
- Assistere l’acquirente nella **visualizzazione** delle informazioni degli articoli tramite **AR (Augmented-Reality)**;
- Fornire **raccomandazioni su promozioni attive** al momento della spesa, in base a ciò che un acquirente ha scansionato durante la spesa corrente;
- Fornire **raccomandazioni di articoli simili** a quelli precedentemente scansionati da un acquirente;
- Fornire **raccomandazioni in base a profili di acquisto simili**;
- Consentire ai gestori del supermercato l'**inserimento e la modifica** delle informazioni associate agli **articoli in vendita**;
- Consentire ai gestori del supermercato l'**inserimento e la modifica delle promozioni** sugli articoli;
- Dare agli acquirenti la possibilità di **registrarsi e autenticarsi** alla piattaforma;
- Permettere la **ricerca esplicita degli articoli** in vendita al supermercato;
- Permettere all'utente di effettuare il **pagamento** degli articoli aggiunti al carrello.
- [Fidelizzazione?]: # (Come si gestisce la fidelizzazione? È necessaria una funzionalità che la descriva?)

Il sistema non dovrà:

- Sostituire alcuna piattaforma di gestione interna già in uso dai gestori del supermercato;
- Vincolare l'acquirente all'uso dell'app per accedere alle promozioni ordinarie.

## Obiettivi e criteri di successo del progetto

Il progetto si pone i seguenti **obiettivi**:

- Migliorare l'esperienza di acquisto dell'acquirente fornendogli tutte le informazioni per la scelta degli articoli con trasparenza.
- Rendere il sistema prodotto usabile per la maggior parte degli acquirenti tipici del cliente.
- Migliorare la fidelizzazione degli acquirenti grazie ai sistemi di supporto e di raccomandazione;
- Incrementare le vendite del cliente grazie al sistema di raccomandazione che favorisce la vendita degli articoli;
- Realizzare il front-end del sistema come app multipiattaforma (almeno Android e iOS), rivolgendosi alla maggior parte del mercato degli smartphone;

Il progetto sarà di successo se, oltre agli obiettivi, rispetta anche i seguenti **criteri di successo**:

- Rispettare tutte le consegne;
- Implementare le funzionalità a proprità alta prima della consegna finale;
- Implementare il sistema usando le tecnologie multipiattaforma Angular, Ionic e Cordova. 

[Capacitor?]: # (Al posto di Cordova si può pensare di usare Capacitor come suggerito da MDS)

## Definizioni, acronimi e abbreviazioni

Alcune definizioni, acronomi e abbreviazioni utili per la lettura del documento:

- **Cliente**: committente del sistema, ovvero la catena di supermercati Coop;
- **Acquirente**: persona che acquista uno o più articoli in un supermercato, da non confrondersi con "cliente" di cui sopra;
- **Filiale**: sinonimo di sede (del supermercato);
- **Gestore**: persona predisposta alla gestione del sistema informativo delle sedi del supermercato;
- **AR**: Augmented Reality, realtà aumentata;
- **RF**: Requisito Funzionale;
- **NFR**: Requisito Non Funzionale;
- **UC**: Use case, caso d'uso;
- **UCD**: Use case diagram, diagramma dei casi d'uso. 

## Riferimenti

Di seguito i riferimenti ad altri documenti relativi al sistema:

- **Problem Statement**, disponibile all'indirizzo: [Problem Statement](https://github.com/EMAD-2019-Accenture/App/blob/master/docs/problem_statement/Problem%20Statement.pdf)

## Panoramica

Nella sezione *Sistema corrente* viene presentata la situazione attuale per affrontare le esigenze del cliente.

Nella sezione *Sistema proposto* viene presentato nel dettaglio il sistema, anzitutto con una panoramica generale, per poi passare ad elencare i requisiti funzionali e non funzionali. Successivamente, si presentano i vari modelli del sistema: gli scenari, il modello dei casi d'uso, il modello ad oggetti, il modello dinamico e l'interfaccia utente.

Infine, nella sezione *Glossario* vengono raccolti tutti i termini tecnici usati nei vari modelli del sistema che necessitano osservazioni affinché il senso e il contesto di utilizzo siano inequivocabili.

# Sistema corrente

Attualmente il cliente dispone di un proprio sistema informativo, con il quale è in grado di inserire o modificare le informazioni degli articoli in vendita, delle promozioni, del magazzino, dei dipendenti, ecc. Il sistema informativo **non si interfaccia in alcun modo con gli acquirenti**.

# Sistema proposto

## Panoramica
Il sistema è composto da due parti principali: 

- un sottosistema **front-end** fornito tramite un'app per smartphone Android e iOS accessibile direttamente dagli acquirenti;
- un sottosistema **back-end** che si occupa dell'interrogazione della base di dati del supermercato tramite opportuni web service. Tutto il back-end è deployato sulla piattaforma Oracle Cloud.

Gli utenti del sistema sono gli Acquirenti e i Gestori.

## Requisiti funzionali

Di seguito sono elencati i requisiti funzionali del sistema. Ciascuno dispone di un identificatore univoco (*RF_n*, dove *n* è un numero intero positivo), di un nome, di una breve descrizione, di una priorità e dell'elenco degli attori partecipanti.

- **RF_1** - **Scannerizzazione barcode**
  - Descrizione: Il sistema permetterà la scannerizzazione dei codici a barre degli articoli
  - Attori partecipanti: Acquirente
  - Priorità: Alta

- **RF_2** - **Visualizzazione informazioni articoli**
  - Descrizione: Il sistema permetterà la visualizzazione dei risultati della scannerizzazione tramite AR
  - Attori partecipanti: Acquirente
  - Priorità: Alta

- **RF_3** - **Avvio sessione di acquisto**
  - Descrizione: Il sistema permetterà l'avvio di una sessione di acquisto, che abilita l'inserimento degli articoli scannerizzati in un carrello
  - Attori partecipanti: Acquirente
  - Priorità: Alta

- **RF_4** - **Aggiunta articoli scansionati nel carrello**
  - Descrizione: Il sistema permetterà l'aggiunta degli articoli scannerizzati al carrello se è stata avviata una sessione di acquisto
  - Attori partecipanti: Acquirente
  - Priorità: Alta

- **RF_5** - **Eliminazione articoli dal carrello**
  - Descrizione: Il sistema permetterà la rimozione di articoli precedentemente inseriti nel carrello
  - Attori partecipanti: Acquirente
  - Priorità: Alta

- **RF_6** - **Modifica quantità articoli del carrello**
  - Descrizione: Il sistema permetterà la modifica della quantità degli articoli presenti nel carrello
  - Attori partecipanti: Acquirente
  - Priorità: Alta
  
- **RF_7** - **Monitoraggio costo articoli carrello** (Non sarebbe meglio uno di visualizzazione del carrello e basta?)
  - Descrizione: Il sistema permetterà all'utente di visionare i prezzi degli articoli e il conseguente costo totale della spesa corrente
  - Attori partecipanti: Acquirente
  - Priorità: Alta

- **RF_8** - **Terminazione sessione di acquisto**
  - Descrizione: Il sistema permetterà la terminazione di una sessione di acquisto attiva
  - Attori partecipanti: Acquirente
  - Priorità: Alta

- **RF_9** - **Ricezione raccomandazioni basate su promozioni**
  - Descrizione: Il sistema invierà raccomandazioni tramite notifiche a seconda delle promozioni attive al momento della scannerizzazione, lasciando decidere all'acquirente se accettarle o meno
  - Attori partecipanti: Acquirente
  - Priorità: Alta

- **RF_10** - **Ricezione raccomandazioni di articoli simili**
  - Descrizione: Il sistema invierà raccomandazioni tramite notifiche a seconda della categoria dell'articolo che è stato scanerizzato, lasciando decidere l'acquirente se accettarle o meno
  - Attori partecipanti: Acquirente
  - Priorità: Alta

- **RF_11** - **Pagamento carrello**
  - Descrizione: Il sistema permetterà al termine di una sessione di acquisto di poter procedere al pagamento degli articoli inseriti nel carrello
  - Attori partecipanti: Acquirente
  - Priorità: Media

- **RF_12** - **Registrazione**
  - Descrizione: Il sistema permetterà agli acquirenti di registrarsi, inserendo alcuni dati personali e di accesso
  - Attori partecipanti: Acquirente
  - Priorità: Media

- **RF_13** - **Login**
  - Descrizione: Il sistema permetterà di compiere il login all'area riservata, permettendo l'accesso ad alcune funzionalità speciali
  - Attori partecipanti: Acquirente, Gestore
  - Priorità: Media

- **RF_14** - **Logout**
  - Descrizione: Il sistema permetterà di uscire dalla propria area riservata, impedendo l'accesso alle funzionalità speciali
  - Attori partecipanti: Acquirente, Gestore
  - Priorità: Media

- **RF_15** - **Ricezione raccomandazioni basate su profili simili**
  - Descrizione: Il sistema invierà raccomandazioni tramite notifiche a seconda di profili utente simili, lasciando decidere l'acquirente se accettarle o meno
  - Attori partecipanti: Acquirente
  - Priorità: Media

- **RF_16** - **Visualizzazione catalogo articoli**
  - Descrizione: Il sistema permetterà l'accesso al catalogo completo degli articoli presenti in tutti i punti vendita
  - Attori partecipanti: Acquirente
  - Priorità: Media

- **RF_17** - **Ricerca articolo nel catalogo**
  - Descrizione: Il sistema permetterà di compiere una ricerca nel catalogo degli articoli basandosi su alcuni filtri
  - Attori partecipanti: Acquirente
  - Priorità: Media

- **RF_18** - **Inserimento di un articolo**
  - Descrizione: Il sistema permetterà di inserire un nuovo articolo al catalogo
  - Attori partecipanti: Gestore
  - Priorità: Media

- **RF_19** - **Modifica informazioni di un articolo**
  - Descrizione: Il sistema permetterà la modifica delle informazioni relativa ad articoli presenti nel catalogo
  - Attori partecipanti: Gestore
  - Priorità: Media

- **RF_20** - **Rimozione di un articolo**
  - Descrizione: Il sistema permetterà di rimuovere un articolo presente nel catalogo
  - Attori partecipanti: Gestore
  - Priorità: Media

## Requisiti non funzionali

Di seguito sono elencati i requisiti non funzionali del sistema, raggruppate per tipologia secondo il *modello FURPS+*. Ciascuno dispone di un identificatore univoco (*NFR__T_n*, dove *T* indica la tipologia ed n è un numero intero positivo), di un nome, una breve descrizione e una priorità.

### Usabilità

- **NFR_U_1** - **Requisiti minimi UX**
  - Descrizione: L'interfaccia utente dell'app rispetterà i requisiti minimi di User Experience (UX) delle piattaforme target
  - Priorità: Alta

- **NFR_U_2** - **Minimo numero di tocchi**
  - Descrizione: L'app permetterà agli utenti di raggiungere le sue funzionalità principali in pochi tocchi e senza necessità di consultare alcun manuale
  - Priorità: Alta

- **NFR_U_3** - **Informazioni minimali**
  - Descrizione: L'insieme di informazioni mostrate agli utenti sarà minimale, contenente solo ciò che è essenziale e senza troppe possibilità di diramazione nel flusso di esecuzione
  - Priorità: Alta

- **NFR_U_4** - **Età differenti**
  - Descrizione: L'app dovrà essere usabile da **utenti di qualsiasi età**, con maggiore attenzione agli anziani, tipici acquirenti di un supermercato
  - Priorità: Bassa

### Affidabilità

- **NFR_R_1** - **Sanificazione dei dati**
  - Descrizione: I dati inviati dal front-end al back-end saranno sanificati sia lato client che lato server al fine di prevenire iniezioni di codice
  - Priorità: Alta

- **NFR_R_2** - **Arresti anomali non distruttivi**
  - Descrizione: Eventuali arresti anomali dell'app non dovranno intaccare in alcun modo i dati presenti sul database nè danneggiare in alcun modo gli utenti
  - Priorità: Alta

- **NFR_R_3** - **Disponibilità 24/7**
  - Descrizione: Il back-end verrà messo in esercizio su una piattaforma cloud, quindi sarà garantita **disponibilità 24/7**
  - Priorità: Media

- **NFR_R_4** - **Carico dati minimale**
  - Descrizione: Il front-end manderà una quantità minimale di dati al back-end, necessari per le interrogazioni al database e per memorizzare alcune informazioni sugli acquirenti, mentre il back-end risponderà con dati di natura prevalentemente testuale, a meno di qualche immagine
  - Priorità: Media

### Prestazioni

- **NFR_P_1** - **Sorgente informativa**
  - Descrizione: La base di dati del sistema, gestita dal back-end, conterrà una **grande quantità di articoli**, usando uno schema molto succinto
  - Priorità: Alta

- **NFR_P_2** - **Ampiezza di banda minimale**
  - Descrizione: L'ampiezza di banda della connessione ad Internet richiesta non sarà alta, soprattutto grazie alla quantità minimale di dati scabiata (NFR_R_4)
  - Priorità: Media

- **NFR_P_3** - **Alta accuratezza raccomandazione**
  - Descrizione: Il calcolo delle raccomandazioni saranno ad altà accuratezza, proponendo effettivamente ciò che può servire all'acquirente
  - Priorità: Media

- **NFR_P_4** - **Tempi di risposta**
  - Descrizione: Il sistema dovrà rispettare i seguenti tempi di risposta:
    - Massimo 3 secondi per l'avvio dell'app;
    - Massimo 0.5 secondi per la lettura del codice a barre;
    - Circa 3 secondi per il recupero dei dati dal server (in caso di connessione veloce).
  - Priorità: Bassa

- **NFR_P_5** - **Buona scalabilità**
  - Descrizione: Si farà in modo che il sistema supporti centinaia di usi contemporanei dei servizi di back-end del sistema, grazie all'altà scalabilità fornita dall'ambiente cloud
  - Priorità: Bassa


### Supportabilità

- **NFR_S_1** - **Disaccoppiamento frond-end e back-end**
  - Descrizione: I sottosistemi front-end e back-end saranno debolmente accoppiati, così che le modifiche di uno non andranno ad impattare sull'altro se non quando vengono coinvolte funzionalità di base che riguardano l'intero sistema
  - Priorità: Alta

- **NFR_S_2** - **App in lingua italiana**
  - Descrizione: Essendo il sistema previsto per Coop Italia, l'app sarà disponibile solo in **lingua italiana**, mentre il back-end del sistema sarà language-agnostic, essendo disaccoppiato dal front-end
  - Priorità: Alta

### Implementazione

- **NFR_C_1** - **App multipiattaforma e back-end su Oracle Cloud**
  - Descrizione: Il back-end sarà messo in esercizio sulla piattaforma Oracle Cloud, mentre l'app sarà disponibile negli store Android e iOS e sviluppata usando Cordova, Angular e Ionic, framework per app multipiattaforma
  - Priorità: Alta

### Interfaccia

- **NFR_C_2** - **Interfacciamento con servizi Oracle Cloud**
  - Descrizione: Il sistema dovrà interfacciarsi con i servizi forniti da Oracle Cloud
  - Priorità: Alta

### Operazioni

- **NFR_C_3** - **Amministrazione e manutenzione a carico del team di sviluppo**
  - Descrizione: l'amministrazione e manutenzione del sistema sarà carico del team di sviluppo
  - Priorità: Bassa

### Packaging

- **NFR_C_4** - **Installazione a carico del team di sviluppo**
  - Descrizione: L'installazione del back-end è carico del team di sviluppo
  - Priorità: Alta

### Legali

- **NFR_C_5** - **Proprietà del cliente**
  - Descrizione: Il sistema sarà di proprietà di Coop che ne ha richiesto lo sviluppo
  - Priorità: Alta

## Modelli del sistema

### Modello dei casi d'uso

| **Nome**                   | UC_1 - Barcode Scan                                                                                                                                                                                                                                                                                                                                                                                                                                 |
|------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Attori  Partecipanti**   | Acquirente                                                                                                                                                                                                                                                                                                                                                                                                                                          |
| **Flusso  di Eventi**      | 1. L' Acquirente seleziona la voce "Scansiona Articolo".<br>&nbsp;&nbsp;2. Il sistema apre la camera per la scansione.<br>3. L'Acquirente inquadra il codice a barre dell'articolo.<br>&nbsp;&nbsp;4. Il sistema, avendo letto correttamente il codice a barre risponde con una serie di informazioni relative all'articolo (codice, categoria, stato, marchio, scadenza,peso lordo, peso netto, durata garanzia, stagionalità, promozione, prezzo promozione) |
| **Condizioni di Ingresso** | L' Acquirente ha avviato il sistema                                                                                                                                                                                                                                                                                                                                                                                                                 |
| **Condizioni d'uscita**    | L' Acquirente visualizza correttamente le informazioni sull'articolo.                                                                                                                                                                                                                                                                                                                                                                                 |
| **Flussi alternativi**    | Al punto 4. Il sistema potrebbe rispondere con un messaggio d'errore dovuto ad errori di rete o all'assenza del'articolo nel DB.                                                                                                                                                                                                                                                                                                                    |


| **Nome**                   | UC_2 - Proposta articoli in promozione |
|------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Attori  Partecipanti**   | Acquirente |
| **Flusso  di Eventi**      | 1. L' Acquirente invia al sistema il codice di un articolo.<br>&nbsp;2. Il sistema ricerca articoli appartenenti alla stessa categoria, presenti nello stessa filiale, che sono in promozione o in prossima scadenza e, se vengono trovati, li propone all'acquirente. |
| **Condizioni di Ingresso** | L' Acquirente ha effettuato UC_1 correttamente |
| **Condizioni d'uscita**    | L'Acquirente visualizza correttamente le promozioni proposte, se ve ne sono |
| **Flussi alternativi**    | Al punto 2. Il sistema potrebbe rispondere con un messaggio d'errore dovuto a problemi di rete |




| **Nome**                   | UC_3 - Aggiunta articoli scansionati al carrello                                                                                                                                                                                                                                       |
|------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Attori  Partecipanti**   | Acquirente                                                                                                                                                                                                                                                                   |
| **Flusso  di Eventi**      | 1. L' Acquirente ha scansionato l'articolo e ne ha ricevuto le informazioni relative.<br>&nbsp; 2. Il sistema mostra un pop-up nel quale chiede all'Acquirente se intende aggiungere l'articolo al proprio carrello.<br> 3. L'Acquirente risponde alla domanda del sistema seleziona l'opzione "Si".<br>&nbsp; 4. Il sistema aggiunge l'articolo al carrello dell'Acquirente e aggiorna l'ammontare complessivo del carrello con il prezzo dell'articolo appena aggiunto|
| **Condizioni di Ingresso** | L' Acquirente ha effettuato UC_1 correttamente.                                                                                                                                                                                                                             |
| **Condizioni d'uscita**    | Il sistema aggiunge correttamente l'articolo scansionato al carrello dell'utente aggiornando senza errori l'ammontare complessivo del carrello.                                                                                                                                                                                                   |
| **Flussi alternativi**    | Al punto 2. Il sistema potrebbe rispondere con un messaggio d'errore dovuto a problemi di rete.<br> Al punto 4. Il sistema potrebbe rispondere con un messaggio d'errore dovuto a problemi di rete o dovuti alla gestione della sessione dell'utente e l'aggiornamento del carrello                                                                                                                                                                      |

| **Nome**                   | UC_4 - Ricerca articolo nel catalogo                                                                                                                                                                                                                                       |
|------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Attori  Partecipanti**   | Acquirente                                                                                                                                                                                                                                                                   |
| **Flusso  di Eventi**      | 1. L' Acquirente inserisce nella barra di ricerca il nome del prodotto che vuole ricercare.<br>&nbsp; 2. Il sistema mostra una lista di elementi che potrebbero corrispondere alla ricerca dell'Acquirente.<br> 3. L'Acquirente seleziona il prodotto di interesse sul quale vuole visualizzare le informazioni.<br>&nbsp; 4. Il sistema, avendo ricevuto l'identificativo del prodotto selezionato dall'utente, risponde con una serie di informazioni relative all'articolo (codice, categoria, stato, marchio, scadenza,peso lordo, peso netto, durata garanzia, stagionalità, promozione, prezzo promozione)|
| **Condizioni di Ingresso** | L'Acquirente ha avviato il sistema.                                                                                                                                                                                                                              |
| **Condizioni d'uscita**    | L'Acquirente visualizza correttamente le informazioni sull'articolo.                                                                                                                                                                                                    |
| **Flussi alternativi**    | Al punto 2. Il sistema potrebbe rispondere con un messaggio d'errore dovuto a problemi di rete.<br> Al punto 2. Il sistema potrebbe notificare l'Acquirente sull'assenza di prodotti in catalogo che matchino con la ricerca da lui effettuata.<br>Al punto 4. Il sistema potrebbe rispondere con un messaggio d'errore dovuto ad errori di rete o all'assenza del'articolo nel DB.             |    



### Modello ad oggetti

### Interfaccia utente - navigational path e mock-up
