import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IonicModule } from '@ionic/angular';
import { CatalogoPageComponent } from './catalogo-page/catalogo-page.component';

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
  ],
  providers: [
  ]
})
export class CatalogoModule { }
