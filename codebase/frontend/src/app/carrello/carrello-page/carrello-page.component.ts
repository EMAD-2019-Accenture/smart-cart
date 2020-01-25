import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/auth/auth.service';
import { Product } from 'src/app/shared/model/product';
import { ToastNotificationService } from 'src/app/shared/toast/toast-notification.service';
import { Cart } from '../../shared/model/cart';
import { CarrelloService } from '../carrello.service';
import { RaccomandazioniService } from '../raccomandazioni.service';
import { ScanService } from '../scan.service';

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
    private authService: AuthService,
    private toastService: ToastNotificationService,
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

  public deactivateCart() {
    this.carrelloService.deactivateCart(this.cart);
  }

  public getTotalPrice(): number {
    return this.carrelloService.getTotalPrice(this.cart);
  }

  public getTotalQuantity(): number {
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
    this.scanService.startNormalScan().then((barcode: string) => {
      this.router.navigateByUrl('/articolo/' + barcode, { state: { scan: true } });
    });
  }

  public navigateToRaccomandazioni() {
    this.recommendationsNumber = 0;
    this.router.navigateByUrl('index/carrello/raccomandazioni');
  }

  /**
   * Get a recommendation from the server
   */
  private getNewRecommendation(): void {
    // TODO: Use a better probability criterion
    const rand = Math.random() >= 0.1;
    if (rand) {
      const productsInCart: Product[] = this.cart.getItems().map(value => value.getProduct());
      this.raccomandazioniService.getNewRecommendation(productsInCart);
      // TODO: Improve toast
      this.toastService.presentToast('C\' è una raccomandazione per te!', 'success');
      this.recommendationsNumber++;
    }
    // TODO: Need a service from AuthService to get logged customer ID instead of username?? Ask Manuel
  }
}
