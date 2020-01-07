import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { IonicModule } from '@ionic/angular';
import { HttpClientModule } from '@angular/common/http';
import { IonicStorageModule } from '@ionic/storage';

import { CarrelloPageComponent } from './carrello-page/carrello-page.component';
import { RaccomandazioniPageComponent } from './raccomandazioni-page/raccomandazioni-page.component';

import { CarrelloService } from './carrello.service';
import { ScanService } from './scan.service';
import { RaccomandazioniService } from './raccomandazioni.service';
import { BarcodeScanner } from '@ionic-native/barcode-scanner/ngx';

const routes: Routes = [
  { path: '', component: CarrelloPageComponent },
  { path: 'raccomandazioni', component: RaccomandazioniPageComponent }
];

@NgModule({
  declarations: [
    CarrelloPageComponent,
    RaccomandazioniPageComponent
  ],
  imports: [
    CommonModule,
    IonicModule,
    RouterModule.forChild(routes),
    IonicStorageModule.forRoot(),
    HttpClientModule
  ],
  providers: [
    CarrelloService,
    ScanService,
    RaccomandazioniService,
    BarcodeScanner,
  ]
})
export class CarrelloModule { }
