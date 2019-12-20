import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { IonicModule } from '@ionic/angular';
import { AuthService } from '../auth/auth.service';
import { PreferenzePageComponent } from './preferenze-page/preferenze-page.component';
import { PreferenzeService } from './preferenze.service';

const routes: Routes = [
  { path: '', component: PreferenzePageComponent }
];

@NgModule({
  declarations: [PreferenzePageComponent],
  imports: [
    CommonModule,
    IonicModule,
    RouterModule.forChild(routes),
    HttpClientModule,
    FormsModule
  ],
  providers: [
    PreferenzeService,
    AuthService
  ]
})
export class PreferenzeModule { }
