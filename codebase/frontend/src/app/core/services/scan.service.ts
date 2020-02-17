import { Injectable, isDevMode } from '@angular/core';
import { BarcodeScanner, BarcodeScanResult } from '@ionic-native/barcode-scanner/ngx';

// tslint:disable: align
@Injectable({
  providedIn: 'root',
})
export class ScanService {

  private scannerOptions = {
    resultDisplayDuration: 0
  };

  constructor(private barcodeScanner: BarcodeScanner) { }

  private scan(): Promise<BarcodeScanResult> {
    return this.barcodeScanner.scan(this.scannerOptions);
  }

  public async startNormalScan(): Promise<string> {
    let scannedBarcode: string;
    if (isDevMode()) {
      scannedBarcode = this.fakeNormalScan();
    } else {
      scannedBarcode = (await this.scan()).text;
    }
    return scannedBarcode;
  }

  public async startSpecificScan(expectedBarcode: string): Promise<boolean> {
    let result: BarcodeScanResult;
    let scannedBarcode: string;
    if (isDevMode()) {
      return true;
    } else {
      do {
        result = await this.scan();
        scannedBarcode = result.text;
      }
      while (!result.cancelled && scannedBarcode !== expectedBarcode);
      return !result.cancelled;
    }
  }

  // DEBUG
  private fakeNormalScan(): string {
    if (Math.random() < 0.5) {
      if (Math.random() < 0.5) {
        return '8041790505131';
      } else {
        return '5449000000439';
      }
    } else {
      if (Math.random() < 0.5) {
        return '8000500340622';
      } else {
        return '8017596062239';
      }
    }
  }
}
