import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { IonicModule } from '@ionic/angular';
import { CatalogoPageComponent } from './catalogo-page/catalogo-page.component';
import { PopoverComponent } from './popover/popover.component';

const routes: Routes = [
  { path: '', component: CatalogoPageComponent }
];

@NgModule({
  declarations: [
    CatalogoPageComponent,
    PopoverComponent
  ],
  entryComponents: [
    PopoverComponent
  ],
  imports: [
    CommonModule,
    IonicModule,
    RouterModule.forChild(routes),
    FormsModule
  ],
  providers: [
  ]
})
export class CatalogoModule { }
