import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LoadingService } from 'src/app/core/services/loading.service';
import { CartItem } from '../../core/model/cart-item';
import { IProduct, Product } from '../../core/model/product';
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

  constructor(private articoloService: ArticoloService,
    private scanService: ScanService,
    private recommendationService: RaccomandazioniService,
    private loadingService: LoadingService,
    private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit() {
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
        this.ingredients = this.parseIngredients(this.product.getIngredients());
        if (history.state.scan) {
          this.cartItem = this.articoloService.makeCartItem(this.product);
        }
      })
      // TODO: Manage the case when the response has nothing
      .catch(reason => {
        this.product = null;
        console.log('Product not found: ' + reason);
      })
      .finally(() => loading.dismiss());
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
          this.recommendationId = undefined;
          // TODO: Decide whether to add brutally into cart
          // this.router.navigateByUrl('/index/carrello', { state: { item: this.preparedCartItem } });
          this.articoloService.makeCartItem(this.product);
        } else {
          console.log('Then but false: when it happens?');
        }
      })
      .catch(reason => {
        console.log('Catch: when it happens? ' + reason);
      });
  }

  // TODO: will be useful as soon it is fixed in the DB? Maybe remove
  private parseIngredients(ingredients: string): string[] {
    return ingredients.split(', ').map(value => value.trim()).filter(value => value !== '' && value !== ' ');
  }
}
