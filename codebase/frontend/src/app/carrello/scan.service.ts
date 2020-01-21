import { isDevMode } from '@angular/core';
import { BarcodeScanner } from '@ionic-native/barcode-scanner/ngx';

// tslint:disable: align
export class ScanService {

  private scannerOptions = {
    resultDisplayDuration: 0
  };

  constructor(private barcodeScanner: BarcodeScanner) { }

  private async scanAndWait(): Promise<string> {
    return (await this.barcodeScanner.scan(this.scannerOptions)).text;
  }

  public startScan(): Promise<string> {
    let scannedBarcodePromise: Promise<string>;
    if (isDevMode()) {
      scannedBarcodePromise = this.fakeScan();
    } else {
      scannedBarcodePromise = this.scanAndWait();
    }
    return scannedBarcodePromise;
  }

  public async startSpecificScan(expectedBarcode: string): Promise<boolean> {
    let scannedBarcode: string;
    do {
      if (isDevMode()) {
        scannedBarcode = await this.fakeScan();
      } else {
        scannedBarcode = await this.scanAndWait();
      }
      // TODO: What is the value of "barcode" is when phone "back" is pressed?
    }
    while (expectedBarcode !== scannedBarcode);
    return true;
  }

  // DEBUG Remove
  private async fakeScan(): Promise<string> {
    return '8001120789761';
  }
}
