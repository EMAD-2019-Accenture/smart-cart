import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { HttpClientModule } from '@angular/common/http';
import { IonicStorageModule } from '@ionic/storage';

import { ArticoloPageComponent } from './articolo-page/articolo-page.component';
import { Routes, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { ArticoloService } from './articolo.service';


const routes: Routes = [
  { path: 'articolo/:id', component: ArticoloPageComponent },
  {
    path: '',
    redirectTo: '/index',
    pathMatch: 'full'
  }
];

@NgModule({
  declarations: [
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
    ArticoloPageComponent
  ],
  providers: [
    ArticoloService
  ]
})
export class SharedModule { }
