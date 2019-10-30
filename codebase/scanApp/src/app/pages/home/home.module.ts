import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { IonicModule } from '@ionic/angular';

import { HomePage } from './home.page';

const routes: Routes = [
  {
    path: 'tabs',
    component: HomePage,
    children: [
      {
        path: 'scan',
        loadChildren: '../scan/scan.module#ScanPageModule'
      },
      {
        path: 'cart',
        loadChildren: '../cart/cart.module#CartPageModule'
      },
      { 
        path: 'settings', 
        loadChildren: '../settings/settings.module#SettingsPageModule'
      },
      { 
        path: 'search', 
        loadChildren: '../search/search.module#SearchPageModule'
      },
    ]
  },
  {
    path: '',
    redirectTo: '/tabs/scan',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes)
  ],
  declarations: [HomePage]
})
export class HomePageModule { }
