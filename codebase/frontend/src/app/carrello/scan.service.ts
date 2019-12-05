import { BarcodeScanner } from '@ionic-native/barcode-scanner/ngx';

export class ScanService {

  private options;

  constructor(private barcodeScanner: BarcodeScanner) {
    this.options = {
      resultDisplayDuration: 0
    };
  }

  public startScan() {
    this.barcodeScanner.scan(this.options).then(barcode => {
      // Debug remove
      console.log('Dal service' + JSON.stringify(barcode));
      return JSON.stringify(barcode);
    }).catch(err => {
      return '';
    });
  }
}
