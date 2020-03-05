import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { BarcodeScanner } from '@ionic-native/barcode-scanner/ngx';
import { IonicModule } from '@ionic/angular';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    IonicModule
  ],
  providers: [
    BarcodeScanner
  ]
})
export class CoreModule { }
