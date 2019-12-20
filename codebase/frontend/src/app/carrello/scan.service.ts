import { HttpClient, HttpHeaders } from '@angular/common/http';
import { isDevMode } from '@angular/core';
import { BarcodeScanner, BarcodeScanResult } from '@ionic-native/barcode-scanner/ngx';
import { Storage } from '@ionic/storage';

// tslint:disable: align
export class ScanService {

  private scannerOptions = {
    resultDisplayDuration: 0
  };

  constructor(private barcodeScanner: BarcodeScanner) {
  }

  // DEBUG Remove
  private async mockScan(): Promise<string> {
    return '8020458000324';
  }

  private async scan(): Promise<string> {
    return (await this.barcodeScanner.scan(this.scannerOptions)).text;
  }


  public async startScan(): Promise<string> {
    let barcode: Promise<string>;
    if (isDevMode()) {
      barcode = this.mockScan();
    } else {
      barcode = this.scan();
    }
    return barcode;
  }
}
