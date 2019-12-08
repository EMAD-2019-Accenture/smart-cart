import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Cart } from '../../shared/model/cart';
import { CarrelloService } from '../carrello.service';
import { ScanService } from '../scan.service';

@Component({
  selector: 'app-carrello-page',
  templateUrl: './carrello-page.component.html',
  styleUrls: ['./carrello-page.component.scss'],
})
// tslint:disable: align
export class CarrelloPageComponent implements OnInit {

  cart: Cart;

  constructor(private carrelloService: CarrelloService,
    private scanService: ScanService,
    private router: Router,
    public activatedRoute: ActivatedRoute) {
    this.cart = this.carrelloService.makeCart();
    activatedRoute.data.subscribe({
      next: () => this.checkNewItem(),
    });
  }

  ngOnInit() {
  }

  private checkNewItem() {
    if (history.state.item) {
      this.carrelloService.addItem(this.cart, history.state.item);
    }
  }

  public activateCart() {
    this.carrelloService.activateCart(this.cart);
  }

  public deactivateCart() {
    this.carrelloService.deactivateCart(this.cart);
  }

  public getTotalPrice() {
    return this.carrelloService.getTotalPrice(this.cart);
  }

  public getTotalQuantity() {
    return this.carrelloService.getTotalQuantity(this.cart);
  }

  public startScan() {
    const barcodePromise = this.scanService.startScan();
    barcodePromise.then((barcode: string) => {
      this.router.navigateByUrl('/articolo/' + barcode, { state: { scan: true } });
    });
  }

  public deleteItem(index: number) {
    this.carrelloService.deleteItem(this.cart, index);
  }

  public increaseItem(index: number) {
    this.carrelloService.increaseItem(this.cart, index);
  }

  public decreaseItem(index: number) {
    this.carrelloService.decreaseItem(this.cart, index);
  }
}
