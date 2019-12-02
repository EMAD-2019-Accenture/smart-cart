import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';

import { PreferenzePageComponent } from './preferenze-page/preferenze-page.component';
import { IonicModule } from '@ionic/angular';

const routes: Routes = [
  { path: '', component: PreferenzePageComponent }
];

@NgModule({
  declarations: [PreferenzePageComponent],
  imports: [
    CommonModule,
    IonicModule,
    RouterModule.forChild(routes)
  ]
})
export class PreferenzeModule { }
