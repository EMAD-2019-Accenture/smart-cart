import { BarcodeScanner } from '@ionic-native/barcode-scanner/ngx';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Storage } from '@ionic/storage';

export class ScanService {

  constructor(private barcodeScanner: BarcodeScanner, private http: HttpClient, private storage: Storage) {
  }

  private makeOptions(token: string) {
    return {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + token,
        'Access-Control-Allow-Origin': 'http://localhost:8100' // DEBUG In production must change
      })
    };
  }

  private processResponse(response) {
    // TODO Dovrebbe fare altro per processare la response dal server non la stampa solo
    console.log('Server response');
    console.log(response);
    return response;
  }

  // DEBUG get allergens
  private mockScan() {
    this.storage.get('ACCESS_TOKEN')
      .then(token => {
        const options = this.makeOptions(token);
        this.http.get('http://localhost:8080/api/allergens', options).subscribe(response => {
          return this.processResponse(response);
        });
      });
  }

  public startScan() {
    this.mockScan();
    /*
    this.barcodeScanner.scan(this.options).then(barcode => {
      console.log('Dal service: ' + JSON.stringify(barcode));
      // TODO code
    }).catch(err => {
      return '';
    });*/
  }
}
