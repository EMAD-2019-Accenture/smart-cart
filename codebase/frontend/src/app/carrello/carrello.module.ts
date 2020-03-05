import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IonicModule } from '@ionic/angular';
import { IonicStorageModule } from '@ionic/storage';
import { CarrelloPageComponent } from './carrello-page/carrello-page.component';
import { RaccomandazioniPageComponent } from './raccomandazioni-page/raccomandazioni-page.component';

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
    IonicStorageModule.forRoot()
  ],
  providers: [
  ]
})
export class CarrelloModule { }
