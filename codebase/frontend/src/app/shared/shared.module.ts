import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { IonicModule } from '@ionic/angular';
import { IonicStorageModule } from '@ionic/storage';
import { ArticoloPageComponent } from './articolo-page/articolo-page.component';
import { ArticoloService } from './articolo.service';
import { HttpCommonService } from './http-common.service';


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
    ArticoloService,
    HttpCommonService
  ]
})
export class SharedModule { }
