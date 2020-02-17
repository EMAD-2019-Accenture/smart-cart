import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PercentDiscount } from 'src/app/core/model/percent-discount';
import { LoadingService } from 'src/app/core/services/loading.service';
import { ToastService } from 'src/app/core/services/toast.service';
import { CartItem } from '../../core/model/cart-item';
import { Product } from '../../core/model/product';
import { ArticoloService } from '../../core/services/articolo.service';
import { RaccomandazioniService } from '../../core/services/raccomandazioni.service';
import { ScanService } from '../../core/services/scan.service';

@Component({
  selector: 'app-articolo-page',
  templateUrl: './articolo-page.component.html',
  styleUrls: ['./articolo-page.component.scss'],
})
// tslint:disable: align
export class ArticoloPageComponent implements OnInit {
  product: Product;
  ingredients: string[];
  cartItem: CartItem;
  recommendationId: number;
  hiddenLists: boolean;

  constructor(private articoloService: ArticoloService,
    private scanService: ScanService,
    private recommendationService: RaccomandazioniService,
    private loadingService: LoadingService,
    private toastService: ToastService,
    private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit() {
    this.hiddenLists = true;
    if (history.state.recommendationId !== undefined) {
      this.recommendationId = history.state.recommendationId;
    }
    const barcode: number = this.route.snapshot.params.id;
    this.getProductByBarcode(barcode);
  }

  private async getProductByBarcode(barcode: number) {
    const loading: HTMLIonLoadingElement = await this.loadingService.presentWait('Attendi...', true);
    this.articoloService.getProductByBarcode(barcode)
      .then(response => {
        this.product = new Product(response);
        // TODO: Fake discounts REMOVE
        // Gran Tramezzino
        if (this.product.getBarcode() === '8017596062239') {
          const pd: PercentDiscount = this.articoloService.getFakeDiscount(0.5);
          this.product.setPercentDiscount(pd);
        }
        // Coca cola
        if (this.product.getBarcode() === '5449000000439') {
          const pd: PercentDiscount = this.articoloService.getFakeDiscount(0.4);
          this.product.setPercentDiscount(pd);
        }

        this.ingredients = this.parseIngredients(this.product.getIngredients());
        if (history.state.scan) {
          this.cartItem = this.articoloService.makeCartItem(this.product);
        }
      })
      .catch(reason => {
        this.product = null;
        let message: string;
        if (reason.status === 404) {
          message = 'Errore: articolo non trovato';
          console.log('Product not found: ' + JSON.stringify(reason));
        } else {
          message = 'Errore: rete assente';
          console.log('Network error: ' + JSON.stringify(reason));
        }
        this.toastService.presentToast(message, 2000, true, 'danger', true);
      })
      .finally(() => loading.dismiss());
  }

  public hasDiscount(): boolean {
    return (this.product.getDiscount().getId() !== undefined)
      && (this.product.getDiscount().getStart() < this.product.getDiscount().getEnd());
  }

  public hasPercentDiscount(): boolean {
    return (this.product.getPercentDiscount().getId() !== undefined)
      && (this.product.getPercentDiscount().getStart() < this.product.getPercentDiscount().getEnd());
  }

  public hasAnyDiscount(): boolean {
    return this.hasDiscount() || this.hasPercentDiscount();
  }

  public hasIngredientsOrAllergens(): boolean {
    return this.product.getIngredients() !== '' || this.product.getAllergens.length > 0;
  }

  public getUnitFullPrice(): number {
    return this.articoloService.getUnitFullPrice(this.product);
  }

  public getFullPrice(): number {
    return this.articoloService.getFullPrice(this.cartItem);
  }

  public getUnitDiscountedPrice(): number {
    return this.articoloService.getUnitDiscountedPrice(this.product);
  }

  public getDiscountedPrice(): number {
    return this.articoloService.getDiscountedPrice(this.cartItem);
  }

  public increaseQuantity() {
    this.articoloService.increaseQuantity(this.cartItem);
  }

  public decreaseQuantity() {
    this.articoloService.decreaseQuantity(this.cartItem);
  }

  public addToCart() {
    this.router.navigateByUrl('/index/carrello', { state: { item: this.cartItem } });
  }

  public startSpecificScan() {
    this.scanService.startSpecificScan(this.product.getBarcode())
      .then(result => {
        if (result) {
          this.recommendationService.acceptRecommendation(this.recommendationId);
          this.recommendationId = null;
          // TODO: Decide whether to add brutally into cart
          // this.router.navigateByUrl('/index/carrello', { state: { item: this.preparedCartItem } });
          this.cartItem = this.articoloService.makeCartItem(this.product);
        } else {
          console.log('Then but false: when it happens?');
        }
      })
      .catch(reason => {
        console.log('Plugin not available - Reason: ' + reason);
      });
  }

  public switchLists() {
    this.hiddenLists = !this.hiddenLists;
  }

  // TODO: will be useful as soon it is fixed in the DB? Maybe remove
  private parseIngredients(ingredients: string): string[] {
    return ingredients.split(', ')
      .map(value => value.trim())
      .filter(value => value !== '' && value !== ' ');
  }
}
