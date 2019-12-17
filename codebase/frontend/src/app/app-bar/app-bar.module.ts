import { NgModule } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule} from '@angular/router';
import { BarraNavigazionaleComponent } from './barra-navigazionale/barra-navigazionale.component';
import { CarrelloPageComponent } from '../carrello/carrello-page/carrello-page.component';
import { CatalogoPageComponent } from '../catalogo/catalogo-page/catalogo-page.component';
import { PreferenzePageComponent } from '../preferenze/preferenze-page/preferenze-page.component';

const routes: Routes = [
  {
    path: 'index',
    component : BarraNavigazionaleComponent,
    children: [
      { path: 'carrello', loadChildren: '../carrello/carrello.module#CarrelloModule'},
      { path: 'catalogo', component: CatalogoPageComponent},
      { path: 'preferenze', component: PreferenzePageComponent}
    ]
  },
  {
    path: '',
    redirectTo: '/index/carrello',
    pathMatch: 'full'
  }
];

@NgModule({
  declarations: [
    BarraNavigazionaleComponent,
    CatalogoPageComponent,
    PreferenzePageComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    IonicModule
  ]
})
export class AppBarModule {}
