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
    path: '',
    component : BarraNavigazionaleComponent,
    pathMatch: 'full',
    children: [
      { path: 'carrello', component: CarrelloPageComponent, pathMatch: 'full'},
      { path: 'catalogo', component: CatalogoPageComponent, pathMatch: 'full' },
      { path: 'preferenze', component: PreferenzePageComponent, pathMatch: 'full' }
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
    CarrelloPageComponent,
    CatalogoPageComponent,
    PreferenzePageComponent,
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    IonicModule
  ],
  exports: [
    RouterModule
  ]
})
export class AppBarModule {}
