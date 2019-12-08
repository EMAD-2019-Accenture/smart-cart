import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { HttpClientModule } from '@angular/common/http';
import { IonicStorageModule } from '@ionic/storage';

import { TabBarComponent } from './tab-bar/tab-bar.component';
import { ArticoloPageComponent } from './articolo-page/articolo-page.component';
import { Routes, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { ArticoloService } from './articolo.service';
import { ActivatedRoute } from '@angular/router';

/*
Va aggiunto sto route

*/

const routes: Routes = [
  { path: 'articolo/:id', component: ArticoloPageComponent },
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
    RouterModule.forChild(routes),
    IonicStorageModule.forRoot(),
    HttpClientModule
  ],
  exports: [
    TabBarComponent,
    ArticoloPageComponent
  ],
  providers: [
    ArticoloService
  ]
})
export class SharedModule { }
