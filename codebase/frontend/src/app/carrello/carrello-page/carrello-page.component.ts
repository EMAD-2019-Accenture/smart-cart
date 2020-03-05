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
import { Recommendation } from 'src/app/core/model/recommendation';
import { ICartItem } from 'src/app/core/model/cart-item';

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
        const scannedItem: ICartItem = history.state.item;
        if (scannedItem) {
          // Uncomment these for: if the added product is new to cart, then evaluate recommendation
          // const oldNumItems = this.cart.getItems().length;
          this.carrelloService.addItem(this.cart, scannedItem);
          // const newNumItems = this.cart.getItems().length;
          // if (newNumItems > oldNumItems) {
          this.checkNewRecommendation(scannedItem);
          // }
        }
      }
    });
  }

  ngOnDestroy() {
    // this.routeSubscription.unsubscribe();
  }

  public activateCart() {
    this.carrelloService.activateCart(this.cart);
  }

  public checkout() {
    let text: string;
    let handler: { (): void; (): void; };
    const checkoutMessage = 'Sessione di acquisto terminata';

    if (this.cart.getItems().length === 0) {
      text = 'Non hai elementi, vuoi comunque chiudere la sessione?';
      handler = () => {
        this.clearSession();
        this.toastService.presentToast(checkoutMessage, 2000, true, 'primary', true);
      };
    } else {
      text = 'Procedere al checkout?';
      handler = () => {
        this.carrelloService.checkout(this.cart);
        this.clearSession();
        this.toastService.presentToast(checkoutMessage, 2000, true, 'primary', true);
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

  private clearSession() {
    this.raccomandazioniService.purgeRecommendations();
    this.cart = this.carrelloService.makeEmptyCart();
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

  private async checkNewRecommendation(scannedItem: ICartItem) {
    /* Uncomment this for better recommendation system
    const productsInCart: Product[] = this.cart.getItems()
      .map(value => value.getProduct());
    */
    // Fake array of product with only the last scanned product
    const productsInCart: Product[] = new Array<Product>();
    productsInCart.push(new Product(scannedItem.product));

    const recommendation: Recommendation = await this.raccomandazioniService.getNewRecommendation(productsInCart);
    if (recommendation !== null) {
      const isAlreadyInCart: boolean = this.cart.getItems()
        .map(value => value.getProduct().getId())
        .includes(recommendation.getProduct().getId(), 0);
      if (!isAlreadyInCart) {
        const added: boolean = this.raccomandazioniService.addRecommendation(recommendation);
        if (added) {
          const message = 'C\'è un articolo per te!';
          this.toastService.presentToast(message, 2000, true, 'primary', true);
          this.recommendationsNumber++;
        } else {
          console.log('Già raccomandato');
        }
      } else {
        console.log('Ce già!');
      }
    }
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
}
