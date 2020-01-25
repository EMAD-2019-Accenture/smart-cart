import { isDevMode } from '@angular/core';
import { BarcodeScanner } from '@ionic-native/barcode-scanner/ngx';

// tslint:disable: align
export class ScanService {

  private scannerOptions = {
    resultDisplayDuration: 0
  };

  constructor(private barcodeScanner: BarcodeScanner) { }

  private async scan(): Promise<string> {
    return (await this.barcodeScanner.scan(this.scannerOptions)).text;
  }

  public async startNormalScan(): Promise<string> {
    let scannedBarcodePromise: Promise<string>;
    if (isDevMode()) {
      scannedBarcodePromise = this.fakeScan();
    } else {
      scannedBarcodePromise = this.scan();
    }
    return scannedBarcodePromise;
  }

  public async startSpecificScan(expectedBarcode: string): Promise<boolean> {
    let scannedBarcode: string;
    do {
      scannedBarcode = await this.startNormalScan();
      // TODO: What is the value of "barcode" is when phone "back" is pressed? In that case this method should return false
    }
    while (expectedBarcode !== scannedBarcode);
    return true;
  }

  // DEBUG
  private async fakeScan(): Promise<string> {
    return '8001120789761';
  }
}
