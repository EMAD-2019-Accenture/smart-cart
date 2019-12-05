import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';

import { CatalogoPageComponent } from './catalogo-page/catalogo-page.component';
import { IonicModule } from '@ionic/angular';

const routes: Routes = [
  { path: '', component: CatalogoPageComponent }
];

@NgModule({
  declarations: [
    CatalogoPageComponent
  ],
  imports: [
    CommonModule,
    IonicModule,
    RouterModule.forChild(routes)
  ]
})
export class CatalogoModule { }
