import { Component, OnInit } from '@angular/core';
import { Carrello } from '../../shared/model/carrello';
import { CarrelloService } from '../carrello.service';
import { ScanService } from '../scan.service';

@Component({
  selector: 'app-carrello-page',
  templateUrl: './carrello-page.component.html',
  styleUrls: ['./carrello-page.component.scss'],
})
export class CarrelloPageComponent implements OnInit {

  carrello: Carrello;

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
    this.scanService.startScan();
  }

  public deleteItem(index: number) {
    // Aggiornare stato persistente
    this.carrelloService.deleteItem(this.carrello, index);
  }

  public increaseItem(carrello: Carrello, index: number) {
    // Aggiornare stato persstente
    this.carrelloService.increaseItem(this.carrello, index);
  }

  public decreaseItem(carrello: Carrello, index: number) {
    // Aggiornare stato persstente
    this.carrelloService.decreaseItem(this.carrello, index);
  }
}
