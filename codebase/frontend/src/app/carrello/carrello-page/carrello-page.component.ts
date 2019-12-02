import { Component, OnInit } from '@angular/core';
import { Cart } from '../../shared/model/cart';
import { CarrelloService } from '../carrello.service';
import { ScanService } from '../scan.service';

@Component({
  selector: 'app-carrello-page',
  templateUrl: './carrello-page.component.html',
  styleUrls: ['./carrello-page.component.scss'],
})
export class CarrelloPageComponent implements OnInit {

  carrello: Cart;

  constructor(private carrelloService: CarrelloService,
              private scanService: ScanService) {
    this.carrello = this.carrelloService.makeCarrello();
  }

  ngOnInit() {

  }

  public activateCarrello() {
    this.carrelloService.activateCarrello(this.carrello);
  }

  public deactivateCarrello() {
    this.carrelloService.deactivateCarrello(this.carrello);
  }

  public startScan() {
    const barcode = this.scanService.startScan();
    // attendere che i dati diventino disponibili (una callback?)
    // Contattare il service di fetch dei dati dal server
  }

  public deleteItem(index: number) {
    // Aggiornare stato persistente
    this.carrelloService.deleteItem(this.carrello, index);
  }

  public increaseItem(carrello: Cart, index: number) {
    // Aggiornare stato persstente
    this.carrelloService.increaseItem(this.carrello, index);
  }

  public decreaseItem(carrello: Cart, index: number) {
    // Aggiornare stato persstente
    this.carrelloService.decreaseItem(this.carrello, index);
  }
}
