# Generare PDF

Per generare il PDF di un documento dal markdown bisogna compiere i seguenti passi:

1. Eseguire `pandoc nome_doc.md -o nome_doc_tmp.md` per risolvere i problemi legati alle tabelle in MD;
2. Copiare i metadati YAML da **nome_doc.md** a **nome_doc_tmp.md**;
3. Eseguire `pandoc -V lang=it-IT nome_doc_tmp.md -o nome_doc_core.pdf` per ottenere il PDF del contenuto principale;
4. Eseguire `rm -rf nome_doc_tmp.md`
5. Copia su Overleaf il file **EMAD_front.tex**, modifica ciò che serve e scarica il file **EMAD_front.pdf** (assicurarsi di avere i file dinf_logo.jpg e app_logo.png);
6. Copia su Overleaf il file **EMAD_tables.tex**, modifica ciò che serve e scarica il file **EMAD_tables.pdf** (assicurarsi di avere i file dinf_logo.jpg e app_logo.png);
7. Usare un tool qualsiasi per unire: **EMAD_front.pdf**, **EMAD_tables.pdf** e **nome_doc_core.pdf**.