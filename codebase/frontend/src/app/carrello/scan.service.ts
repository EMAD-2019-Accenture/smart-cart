import { BarcodeScanner } from '@ionic-native/barcode-scanner/ngx';

export class ScanService {

  constructor(private barcodeScanner: BarcodeScanner) { }

  public startScan() {
    console.log('Mostro lo scanner');
    this.barcodeScanner.scan().then(barcodeData => {
      console.log('Barcode data', JSON.stringify(barcodeData));
    }).catch(err => {
      console.log('Error', err);
    });
    console.log('Mostrato con successo');
  }
}
