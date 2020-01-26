import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ArticoloService } from '../../core/services/articolo.service';
import { RaccomandazioniService } from '../../core/services/raccomandazioni.service';
import { ScanService } from '../../core/services/scan.service';
import { CartItem } from '../model/cart-item';
import { IProduct, Product } from '../model/product';

@Component({
  selector: 'app-articolo-page',
  templateUrl: './articolo-page.component.html',
  styleUrls: ['./articolo-page.component.scss'],
})
// tslint:disable: align
export class ArticoloPageComponent implements OnInit {
  product: Product;
  ingredients: string[];
  preparedCartItem: CartItem;
  recommendationId: number;

  constructor(private articoloService: ArticoloService,
    private scanService: ScanService,
    private recommendationService: RaccomandazioniService,
    private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit() {
    if (history.state.recommendationId !== undefined) {
      this.recommendationId = history.state.recommendationId;
    }
    const barcode: number = this.route.snapshot.params.id;
    this.articoloService.getProductByBarcode(barcode)
      .then((response: IProduct) => {
        this.product = new Product(response);
        this.ingredients = this.parseIngredients(this.product.getIngredients());
        if (history.state.scan) {
          this.prepareCartItem();
        }
      })
      // TODO: Manage the case when the response has nothing
      .catch(reason => console.log('Product not found: ' + reason));
  }

  private prepareCartItem() {
    this.preparedCartItem = new CartItem();
    this.preparedCartItem.setProduct(this.product);
    this.preparedCartItem.setQuantity(1);
  }

  public increaseQuantity() {
    this.articoloService.increaseQuantity(this.preparedCartItem);
  }

  public decreaseQuantity() {
    this.articoloService.decreaseQuantity(this.preparedCartItem);
  }

  public addToCart() {
    this.router.navigateByUrl('/index/carrello', { state: { item: this.preparedCartItem } });
  }

  public startSpecificScan() {
    this.scanService.startSpecificScan(this.product.getBarcode())
      .then(result => {
        if (result) {
          this.recommendationService.acceptRecommendation(this.recommendationId);
          this.recommendationId = undefined;
          // TODO: Decide whether to add brutally into cart
          // this.router.navigateByUrl('/index/carrello', { state: { item: this.preparedCartItem } });
          this.prepareCartItem();
        } else {
          console.log('Then but false: when it happens?');
        }
      }).catch(reason => {
        console.log('Catch: when it happens? ' + reason);
      });
  }

  // TODO: will be useful as soon it is fixed in the DB? Maybe remove
  private parseIngredients(ingredients: string): string[] {
    return ingredients.split(', ').map(value => value.trim()).filter(value => value !== '' && value !== ' ');
  }
}
