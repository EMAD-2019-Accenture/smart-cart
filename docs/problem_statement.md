# Problem Statement

## Dominio del Problema

La grande distribuzione rappresenta il principale mezzo di vendita al dettaglio di prodotti di qualsiasi categoria. Il suo successo deriva dalla grande varietà di prodotti in vendita, dalla loro grande disponibilità e dalla grande diffusione di filiali, quali supermercati o ipermercati, nel terrotorio (locale, regionale, nazionale o internazionale).

Ciò nonostante, la grande distribuzione viene sempre vista come un'entità lontana dai clienti e dai loro bisogni, ed interessata unicamente al profitto.

Bisogna, inoltre, tenere in conto la forte concorrenza da parte di altre catene, che, soprattutto in alcuni territori, sono portate a gareggiare per offrire l'offerta più vantaggiosa per il cliente. Non solo, ma gli anni recenti hanno visto anche l'ingresso nel mercato del settore del commercio elettronico (e-commerce), che, con ottimi risultati, riesce a mettere insieme tutti i vantaggi della grande distribuzione (alta disponbilità dei prodotti, alta qualità del servizio clienti e prezzi vantaggiosi) con la comodità dell'acquisto direttamente da casa. La domanda sugli e-commerce è salita così tanto che sono nati dei servizi premium che danno la possibilità di poter ricevere a casa un ordine il giorno seguente alla conferma, se non addirittura il giorno stesso.

Una catena di supermercati che vuole stare al passo con i tempi non può ignorare l'esistenza di un mondo costantemente connesso e le grandi disponibilità tecnologiche. Sarebbe d'aiuto un sistea che favorisca la fidelizzazione del cliente, aiutandolo negli acquisti e proponendogli anche prodotti simili a quelli che ha già acquistato in passato o che sta per acquistare.

## Sistema proposto

Viene proposta un'app per smartphone a supporto degli acquisti in una singola catena di supermercati. L'app vuole dare la possibilità ai clienti di poter leggere il codice a barre dei prodotti presenti sugli scaffali con il proprio smartphone e di poterne ottenere delle informazioni dettagliate, sia quelle già presenti sull'etichetta (allergenti, valori nutrizionali) che altre aggiuntive, come le valutazioni di altri acquirenti e suggerimenti di acquisto di prodotti correlati.

L'app sarà la parte front-end del sistema che si occuperà dell'interazione utente e dell'ottenimento delle richieste dei clienti, mentre il database dei prodotti e l'elaborazione ad esso associato saranno collocati nella parte back-end del sistema, messa in esercizio sulla piattaforma Oracle Cloud.

## Scenari

### Scenario 1: Scansione prodotto

**Istanze di attori partecipanti**: *Matteo (Acquirente)*
**Flusso di Eventi:**

1. Matteo sta facendo la spesa al supermercato ed è particolarmente interessato ad un set di chiavi inglesi. Avendo scaricato l'app del supermercato, decide di scansionare il codice a barre del prodotto per saperne di più e per conoscere le valutazioni degli altri acquirenti.
2. Una volta scansionato il prodotto, l'app fornisce a matteo varie informazioni(*non so bene quali*), tra cui le valutazioni degli altri aquirenti dello stesso prodotto.
3. Matteo, a questo punto, riceve una notifica dall'app che gli consiglia una cassetta degli attrezzi che in quel momento è in offerta al supermercato.
4. Matteo, incuriosito, scansiona anche la cassetta degli attrezzi e decide, dopo averne letto le informazioni e le valutazioni, di acquistare anche quest'ultima.

### Scenario 2: Promozioni Personalizzate

**Istanze di attori partecipanti**: *Matteo (Acquirente), Luigi (Gestore Supermercato)*.
**Flusso di eventi**:

1. Luigi viene incaricato di inserire sul sistema una nuova offerta per le fette biscottate. Luigi, dunque, si colleca alla piattaforma tramite la sua interfaccia dedicata, ricerca il prodotto in questione, ed inserisce una nuova offerta.
2. Matteo, che ha acquistato prodotti simili a quelli ora messi in offerta da Luigi, riceve una notifica che lo avvisa della nuova promozione. Dunque si collega all'app e ne prende visione. 


## Requisiti Funzionali

- Il sistema deve permettere all'acquirente di recuperare informazioni sui prodotti del supermercato tramite scansione di codice a barre
- Il sistema deve fornire raccomandazioni su offerte agli acquirenti in base a ciò che hanno precedentemente scansionato
- Il sistema deve fornire raccomandazioni di prodotti similari a quelli precedentemente scansionati dall'acquirente
- Il sistema deve inviare raccomandazioni agli acquirenti in base a profili di acquirenti simili
- Il sistema deve fornire ai gestori del supermercato di gestire i prodotti in vendita e di inserire e modificare le varie informazioni relative ad essi
- Il sistema deve fornire ai gestori del supermercato la possibilità di inserire offerte a sui prodotti in vendita.
- Il sistema deve fornire agli acquirenti la possibilità di registrarsi e autenticarsi alla piattaforma.

## Requisiti non funzionali

### Usabilità

L'interfaccia utente dell'app rispetterà i requisiti minimi di User Experience (UX) delle piattaforme target.

L'app dovrà essere usabile da utenti di qualsiasi età, con maggiore attenzione agli anziani, tipici clienti di un supermercato. L'app permetterà agli utenti di raggiungere le sue funzionalità principali in pochi tocchi e senza necessità di consultare alcun manuale. Sarà fornito un breve tutorial opzionale interno all'app per guidare gli utenti meno esperto.

L'insieme di informazioni mostrate agli utenti sarà minimale, contenente solo ciò che è essenziale e senza troppe possibilità di diramazione nel flusso di esecuzione.

### Dependability

Il back-end è messo in esercizio su una piattaforma cloud, quindi sarà garantita disponibilità 24/7.

L'app è sempre consultabile per ottenere informazioni sui prodotti già acquistati o mai acquistati, anche se tutti i punti vendita della catena sono chiusi.

L'utente manderà esplicitamente pochi dati al back-end i quali saranno tutti sanificati al fine di prevenire iniezioni di codice.
Eventuali arresti anomali dell'app non dovranno intaccare in alcun modo i dati presenti sul database nè danneggiare in alcun modo l'utente.

### Performance

La quantità di informazioni che l'app manda al back-end è minimale, principalmente i codice a barre dei prodotti scansionati o alcuni parametri di ricerca. Le risposte del back-end sono prevalentemente di natura testuale, in aggiunta a qualche immagine relativa ai prodotti scansionati e suggeriti. L'ampiezza di banda della connessione ad Internet richiesta non è alta.

Forte di questo, i tempi di risposta del sistema dovranno eseere di:

- Massimo 3 secondi per l'avvio dell'app;
- Massimo 0.5 secondi per la lettura del codice a barre;
- Circa 3 secondi per il recupero dei dati dal server (in caso di connessione veloce). Questo sarà in tempo di calcolo più lungo poiché è inclusa la fase di calcolo dei suggerimento di acquisto.

Essendo il back-end sul cloud, ci si affiderà all'alta scalabilità di quest'ultimo per gestire un alto numero di richieste contemporanee. Nei più grandi punti vendita ci si aspettano decine di usi contemporanei dei servizi di back-end del sistema.

La sorgente informativa, che sarà fornita direttamente dalla catena partecipante, sarà il punto di partenza per la costruzione del database mantenuto sul cloud. La sua dimensione dipenderà dalla ricchezza della sorgente, e dovrà sicuramente prevedere una grande quantità di prodotti, ciascuno con le proprie informazioni di base e le relative recensioni.

Il calcolo dei suggerimento di acquisto dovrà avere un'alta accuratezza, cercando di suggerire ciò che effettivamente può servire al cliente.

### Supportability

Il sistema è composto da front-end e back-end debolmente accoppiati, sviluppati contestualmente ma manutenuti in modo separato, così che le modifiche di uno non andranno ad impattare sull'altro se non quando vengono coinvolte funzionalità di base che coinvolgono l'intero sistema.

I gestori della catena disporranno di un'interfaccia ad-hoc per modificare il catalogo ed altre impostazioni senza la necessità di contattare l'amministrazione del sistema, che invece avrà accessoal back-end tramite altre interfacce di gestione.

Essendo il sistema previsto per una singola catena di supermercati italiana, l'app sarà disponibile solo in lingua italiana, mentre il back-end del sistema sarà language-agnostic, essendo disaccoppiato dal front-end.

### Vincoli

- Implementazione: il back-end sarà messo in esercizio sulla piattaforma Oracle Cloud, mentre l'app sarà disponibile negli store Android e iOS e sviluppata usando Cordova, Angular e Ionic, framework per app multipiattaforma;
- Interfacce: il sistema dovrà interfacciarsi con i servizi cloud forniti da Oracle Cloud;
- Operazioni: l'amministrazione e manutenzione del sistema è carico del team di sviluppo;
- Packaging: l'installazione del sistema è carico del team di sviluppo;
- Legali: il sistema sarà di proprietà del cliente che ne ha richiesto lo sviluppo, ovvero della catena di supermercati servita.
