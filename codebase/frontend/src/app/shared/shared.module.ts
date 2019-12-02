import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';

import { TabBarComponent } from './tab-bar/tab-bar.component';
import { ArticoloPageComponent } from './articolo-page/articolo-page.component';
import { Routes, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

const routes: Routes = [
  {
    path: '',
    component: TabBarComponent,
    children: [
      {
        path: 'tab1',
        redirectTo: '/catalogo',
        pathMatch: 'full'
      },
      {
        path: 'tab2',
        redirectTo: '/carrello',
        pathMatch: 'full'
      },
      {
        path: 'tab3',
        redirectTo: '/preferenze',
        pathMatch: 'full'
      },
      {
        path: '',
        redirectTo: '/tabs/tab2',
        pathMatch: 'full'
      }
    ]
  },
  {
    path: '',
    redirectTo: '/tabs/tab2',
    pathMatch: 'full'
  }
];


@NgModule({
  declarations: [
    TabBarComponent,
    ArticoloPageComponent
  ],
  imports: [
    CommonModule,
    IonicModule,
    FormsModule,
    RouterModule.forChild(routes)
  ],
  exports: [
    TabBarComponent,
    ArticoloPageComponent
  ]
})
export class SharedModule { }
