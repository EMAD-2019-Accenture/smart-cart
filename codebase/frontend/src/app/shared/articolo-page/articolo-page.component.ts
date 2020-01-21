import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { resolve } from 'q';
import { ScanService } from 'src/app/carrello/scan.service';
import { ArticoloService } from '../articolo.service';
import { CartItem } from '../model/cart-item';
import { IProduct, Product } from '../model/product';
import { Recommendation } from '../model/recommendation';

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
  recommendation: Recommendation;

  constructor(private articoloService: ArticoloService,
    private scanService: ScanService,
    private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit() {
    if (history.state.recommendation) {
      this.recommendation = new Recommendation(history.state.recommendation);
    }
    const barcode: number = this.route.snapshot.params.id;
    this.articoloService.getProductByBarcode(barcode).then((response: IProduct) => {
      this.product = new Product(response);
      this.ingredients = this.parseIngredients(this.product.getIngredients());
      if (history.state.scan) {
        this.prepareCartItem();
      }
    });
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
    this.scanService.startSpecificScan(this.recommendation.getProduct().getBarcode()).then((result) => {
      if (result) {
        // TODO: Decide whether to add brutally into cart
        // this.router.navigateByUrl('/index/carrello', { state: { item: this.preparedCartItem } });
        this.recommendation = undefined;
        this.prepareCartItem();
      } else {
        // TODO: What to do?
        console.log('Then but false: when it happens?');
      }
    }).catch((reason) => {
      // TODO: What to do?
      console.log('Catch: when it happens? ' + resolve);
    });
  }

  // TODO: will be useful as soon it is fixed in the DB?
  private parseIngredients(ingredients: string): string[] {
    return ingredients.split(', ').map(value => value.trim()).filter(value => value !== '' && value !== ' ');
  }
}
