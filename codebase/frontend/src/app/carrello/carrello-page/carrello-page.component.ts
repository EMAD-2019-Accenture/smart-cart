import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AlertService } from 'src/app/core/services/alert.service';
import { ToastService } from 'src/app/core/services/toast.service';
import { Cart } from '../../core/model/cart';
import { Product } from '../../core/model/product';
import { RaccomandazioniService } from '../../core/services/raccomandazioni.service';
import { ScanService } from '../../core/services/scan.service';
import { CarrelloService } from '../carrello.service';

@Component({
  selector: 'app-carrello-page',
  templateUrl: './carrello-page.component.html',
  styleUrls: ['./carrello-page.component.scss'],
})
// tslint:disable: align
export class CarrelloPageComponent implements OnInit, OnDestroy {
  cart: Cart;
  recommendationsNumber: number;
  private routeSubscription: Subscription;

  constructor(private carrelloService: CarrelloService,
    private scanService: ScanService,
    private raccomandazioniService: RaccomandazioniService,
    private toastService: ToastService,
    private alertService: AlertService,
    private router: Router,
    private activatedRoute: ActivatedRoute) {
    this.cart = this.carrelloService.makeEmptyCart();
    this.recommendationsNumber = 0;
  }

  ngOnInit() {
    this.routeSubscription = this.activatedRoute.data.subscribe({
      next: () => {
        if (history.state.item) {
          this.carrelloService.addItem(this.cart, history.state.item);
          this.getNewRecommendation();
        }
      }
    });
  }

  ngOnDestroy() {
    this.routeSubscription.unsubscribe();
  }

  public activateCart() {
    this.carrelloService.activateCart(this.cart);
  }

  public checkout() {
    let text: string;
    let handler;
    if (this.cart.getItems().length === 0) {
      text = 'Non hai elementi, vuoi comunque chiudere la sessione?';
      handler = () => this.cart = this.carrelloService.makeEmptyCart();
    } else {
      text = 'Procedere al checkout?';
      handler = () => {
        this.carrelloService.checkout(this.cart);
        this.cart = this.carrelloService.makeEmptyCart();
      };
    }
    this.alertService.presentConfirm(text, [
      {
        text: 'Annulla',
        role: 'cancel',
      },
      {
        text: 'Conferma',
        handler
      }
    ]);
  }

  public getUnitFullPrice(index: number): number {
    return this.carrelloService.getUnitFullPrice(this.cart, index);
  }

  public getFullPrice(index: number): number {
    return this.carrelloService.getFullPrice(this.cart, index);
  }

  public getUnitDiscountedPrice(index: number): number {
    return this.carrelloService.getUnitDiscountedPrice(this.cart, index);
  }

  public getDiscountedPrice(index: number): number {
    return this.carrelloService.getDiscountedPrice(this.cart, index);
  }

  public getCartPrice(): number {
    return this.carrelloService.getTotalDiscountedPrice(this.cart);
  }

  public getTotalQuantities(): number {
    return this.carrelloService.getTotalQuantity(this.cart);
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

  public navigateToScan() {
    this.scanService.startNormalScan()
      .then((barcode: string) => {
        if (barcode) {
          this.router.navigateByUrl('/articolo/' + barcode, { state: { scan: true } });
        } else {
          console.log('Empty barcode');
        }
      })
      .catch(reason => console.log('Scan failed: ' + reason));
  }

  public navigateToRaccomandazioni() {
    this.recommendationsNumber = 0;
    this.router.navigateByUrl('index/carrello/raccomandazioni');
  }

  // TODO: Rete assente error da gestire?
  private getNewRecommendation(): void {
    const productsInCart: Product[] = this.cart.getItems()
      .map(value => value.getProduct());
    this.raccomandazioniService.getNewRecommendation(productsInCart)
      .then(recomm => {
        if (recomm) {
          this.toastService.presentToast('C\'è un articolo per te!', 2000, true, 'success', true);
          this.recommendationsNumber++;
        }
      });
  }
}
