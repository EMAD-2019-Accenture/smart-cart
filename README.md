- [Smart Cart <ins>ITA</ins>](#smart-cart-ita)
  * [Sistema proposto](#sistema-proposto)
    + [Obiettivi e criteri di successo del progetto](#obiettivi-e-criteri-di-successo-del-progetto)
  * [Documentazione progetto](#documentazione-progetto)
    + [Presentazioni](#presentazioni)
  * [Setup](#setup)
    + [Creazione Progetto](#creazione-progetto)
    + [Run App](#run-app)
        * [Fix Icone e Immagini](#fix-icone-e-immagini)

# Smart Cart (ITA)
Il progetto è sviluppato nel contesto del corso di esame [Enterprise Mobile Application Development](https://docenti.unisa.it/004763/didattica?anno=2016&id=511231&cId=10004-2015&pId=N0*N0*S1) (**EMAD**) presso l'Università degli Studi di Salerno, in collaborazione con [Accenture Itaia](https://www.accenture.com). \
Il progetto ha come fine ultimo quello di realizzare un'applicazione in ambito enterprise che prenda parte alla competizione [App Challange](https://it-it.facebook.com/pages/category/Event/App-Challenge-Sfida-allultima-App-UNISA-2034368809926084/) organizzata dalla prof.ssa [Rita Francese](https://docenti.unisa.it/004763/home) e il [Dipartimento di Informatica](https://corsi.unisa.it/informatica).

## Sistema proposto
Viene proposta un'app per smartphone a supporto degli acquisti in una singola catena di supermercati. L'app vuole dare la possibilità ai clienti di poter leggere il codice a barre dei prodotti presenti sugli scaffali con il proprio smartphone e di poterne ottenere delle informazioni dettagliate, sia quelle già presenti sull'etichetta (allergeni, valori nutrizionali, ecc.), sia altre aggiuntive, come le valutazioni di altri acquirenti e suggerimenti dei prodotti. Il sistema sarà di supporto sia alle vendite del supermercato, proponendo articoli per i quali è necessario aumentare le vendite, sia al compratore, al quale saranno presentate delle offerte sui prodotti. Il sistema suggerirà prodotti correlati a quelli visionati dal cliente durante la sessione di acquisto corrente, e prodotti da vendere secondo le priorità del supermarket (scadenze vicine, prodotti poco venduti, ecc.)

Il sistema si compone di un back-end, che verrà messo in esercizio sul servizio PaaS [Heroku](https://www.heroku.com/), e di un front-end, un'app mobile multipiattaforma (Android, iOS).

### Obiettivi e criteri di successo del progetto

Il progetto si pone i seguenti **obiettivi**:

- Migliorare l'esperienza di acquisto dell'acquirente fornendogli tutte le informazioni per la scelta degli articoli con trasparenza;
- Rendere il sistema prodotto usabile per la maggior parte degli acquirenti tipici del cliente;
- Migliorare la fidelizzazione degli acquirenti grazie ai sistemi di supporto e di raccomandazione;
- Incrementare le vendite di Coop grazie al sistema di raccomandazione che favorisce la vendita degli articoli;
- Realizzare il front-end del sistema come app multipiattaforma (almeno Android e iOS), rivolgendosi alla maggior parte del mercato degli smartphone;

Il progetto sarà di successo se, oltre agli obiettivi, rispetta anche i seguenti **criteri di successo**:

- Rispettare tutte le consegne;
- Implementare le funzionalità a proprità alta prima della consegna finale;
- Implementare il sistema usando le tecnologie multipiattaforma [Angular](https://angular.io/), [Ionic](https://ionicframework.com/) e [Capacitor](https://capacitor.ionicframework.com/).

## Documentazione progetto
Sono riportati di seguito i documenti preliminari alla realizzazione dell'applicativo:

- [Problem Statement](https://github.com/EMAD-2019-Accenture/Smart-Cart/blob/master/docs/problem_statement/problem_statement.md)
- [Requirements Analysis Document](https://github.com/EMAD-2019-Accenture/Smart-Cart/blob/master/docs/rad/rad.md)

### Presentazioni
Sono riportate di seguito le presentazioni realizzate per presentare i vari stadi di avanzamento dell'applicativo:

[Presentazione App Challange](https://docs.google.com/presentation/d/1GQsWLQSfWDhM_0LFhZG48rJcqyhhnBDaPY3lhChTYj4/edit?usp=sharing)

---

## Setup
Di seguito sono presentati gli step necessari per la **creazione del progetto frontend** e successivamente quelli necessari alla **compilazione** e al ***run*** dell'applicazione.

### Creazione Progetto

1. `ionic start frontend blank --type=ionic-angular`
2. `ionic integrations enable capacitor`
3. `npm install @ionic-native/barcode-scanner`
4. `npm install phonegap-plugin-barcodescanner`

### Run App

1. Assicurarsi di essere nella directory `frontend/`
2. Eseguire `npm install` per installare tutte le dipendenze presenti in *package.json*. Verrà creata la directory `nome_modules/`
3. Eseguire `ionic capacitor add` e selezionare dal wizard le piattaforme target desiderate
4. Eseguire `ionic build` per compilare il progetto col profilo development (`ionic build --prod` per il profilo production). Verrà creata la directory `www/` contenente tutti gli asset necessari per il deployment dell'app (indipendentemente dalla piattaforma target);
5. Eseguire `ionic capacitor sync` per copiare i web asset di `www/` in tutte le piattaforme installate, con annessa risoluzione di dipendenze e di installazione di plugin Capacitor/Cordova.
6. Eseguire `ionic capacitor run` e selezionare la piattaforma target desiderata per aprire l'IDE di riferimento della piattaforma scelta con il profilo development (`ionic capacitor run --prod` per eseguire con il profilo production se l'app è stata compilata per production).

In caso di esecuzione della piattaforma Android, assicurarsi di avere installato Android Studio. Se `ionic capacitor run` segnala l'assenza di Android Studio, bisogna inserire nel file *capacitor.config.json* l'attributo `linuxAndroidStudioPath` con il path per lo script di Android Studio (ad es. `/opt/android-studio/bin/studio.sh`).

##### Fix Icone e Immagini

Per **rigenerare le icone** è necessario seguire i seguenti passi:

1. Scegliere un'immagine png 1024x1024px per `resources/icon.png`.
2. Scegliere un'immagine png 2732x2732px per `resources/splash.png`.
3. In `codebase/frontend`, eseguire il comando `npm run resources`.

Con Capacitor, potrebbe essere necessario rimuovere dal manifest principale `android:theme="@style/AppTheme.NoActionBarLaunch"` dal tag `<activity>` per rimuovere uno dei due splash screen che compaiono.

Dettagli su: `https://gist.github.com/dalezak/a6b1de39091f4ace220695d72717ac71#file-resources-js`

Se eventualmente le **immagini non sono visualizzate correttamente** è necessario aggiungere l'attributo `android:usesCleartextTraffic="true"` nel tag `<application>` nel manifest dell'applicazione. 
