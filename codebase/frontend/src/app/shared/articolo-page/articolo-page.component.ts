import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ArticoloService } from '../articolo.service';
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
  fromScan: boolean;
  itemToAdd: CartItem;

  constructor(private articoloService: ArticoloService,
    private router: Router,
    private route: ActivatedRoute) {
    this.product = new Product();
    const barcode: number = this.route.snapshot.params.id;
    this.articoloService.getProduct(barcode).then((response: IProduct) => {
      this.updateView(response);
    });
  }

  ngOnInit() {
  }

  private updateView(product: IProduct) {
    this.product = new Product(product);
    this.parseIngredients(product.ingredients);
    this.fromScan = history.state.scan;
    if (this.fromScan) {
      this.itemToAdd = new CartItem();
      this.itemToAdd.setProduct(this.product);
      this.itemToAdd.setQuantity(1);
    }
  }

  private parseIngredients(ingredients: string) {
    this.ingredients = ingredients.split(', ');
    this.ingredients = this.ingredients.filter(value => value !== '' && value !== ' ');
  }

  public increaseQuantity(index: number) {
    this.articoloService.increaseQuantity(this.itemToAdd);
  }

  public decreaseQuantity(index: number) {
    this.articoloService.decreaseQuantity(this.itemToAdd);
  }

  public addToCart() {
    this.router.navigateByUrl('/carrello', { state: { item: this.itemToAdd } });
  }
}
