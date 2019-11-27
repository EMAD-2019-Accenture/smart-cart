La directory `src/` contiene il progetto Ionic + Angular.
L'integrazione con Capacitor è già presente e attivata.

# Compilare e lanciare l'app

1. Assicurarsi di essere nella directory `scanApp/`
2. Eseguire `npm install` per installare tutte le dipendenze presenti in *package.json*. Verrà creata la directory `nome_modules/`
3. Eseguire `ionic capacitor add` e selezionare dal wizard le piattaforme target desiderate
4. Eseguire `ionic build` per compilare il progetto. Verrà creata la directory `www/` contenente tutti gli asset necessari per il deployment dell'app (indipendentemente dalla piattaforma target);
5. Eseguire `ionic capacitor sync` per copiare copiare i web asset in tutte le piattaforme installate, con annessa risoluzione di dipendenze e di installazione di plugin Capacitor/Cordova.
6. Eseguire `ionic capacitor run` e selezionare la piattaforma target desiderata. Assicurarsi di avere l'IDE di riferimento della piattaforma scelta

In caso di esecuzione della piattaforma Android, assicurarsi di avere installato Android Studio. Se `ionic capacitor run` segnala l'assenza di Android Studio, bisogna inserire nel file *capacitor.config.json* l'attributo `linuxAndroidStudioPath` con il path per lo script di Android Studio (ad es. `/opt/android-studio/bin/studio.sh`).