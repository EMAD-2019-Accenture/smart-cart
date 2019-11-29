import { Component, OnInit } from '@angular/core';
import { BarcodeScanner } from '@ionic-native/barcode-scanner/ngx';

@Component({
  selector: 'app-scan',
  templateUrl: './scan.page.html',
  styleUrls: ['./scan.page.scss'],
})
export class ScanPage implements OnInit {

  constructor(private barcodeScanner: BarcodeScanner) {
    console.log('Costruisco roba');
  }

  ngOnInit() {
    this.showScanner();
  }

  private showScanner() {
    console.log('Mostro lo scanner');
    this.barcodeScanner.scan().then(barcodeData => {
      console.log('Barcode data', JSON.stringify(barcodeData));
    }).catch(err => {
      console.log('Error', err);
    });
    console.log('Mostrato con successo');
  }

}
