import { NgModule } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule} from '@angular/router';
import { BarraNavigazionaleComponent } from './barra-navigazionale/barra-navigazionale.component';

const routes: Routes = [
  {
    path: 'index',
    component : BarraNavigazionaleComponent,
    children: [
      { path: 'carrello', loadChildren: () => import('../carrello/carrello.module').then(m => m.CarrelloModule) },
      { path: 'catalogo', loadChildren: () => import('../catalogo/catalogo.module').then(m => m.CatalogoModule)},
      { path: 'preferenze', loadChildren: () => import('../preferenze/preferenze.module').then(m => m.PreferenzeModule)}
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
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    IonicModule
  ]
})
export class AppBarModule {}
