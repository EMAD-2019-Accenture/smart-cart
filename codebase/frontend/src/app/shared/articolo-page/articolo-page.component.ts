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
  itemToAdd: CartItem;

  constructor(private articoloService: ArticoloService,
    private router: Router,
    private route: ActivatedRoute) {
  }

  ngOnInit() {
    const barcode: number = this.route.snapshot.params.id;
    this.articoloService.getProductByBarcode(barcode).then((response: IProduct) => {
      this.updateView(response);
    });
  }

  private updateView(iProduct: IProduct) {
    this.product = new Product(iProduct);
    this.ingredients = this.parseIngredients(this.product.getIngredients());
    if (history.state.scan) {
      this.itemToAdd = new CartItem();
      this.itemToAdd.setProduct(this.product);
      this.itemToAdd.setQuantity(1);
    }
  }

  private parseIngredients(ingredients: string): string[] {
    return ingredients.split(', ').map(value => value.trim()).filter(value => value !== '' && value !== ' ');
  }

  public increaseQuantity() {
    this.articoloService.increaseQuantity(this.itemToAdd);
  }

  public decreaseQuantity() {
    this.articoloService.decreaseQuantity(this.itemToAdd);
  }

  public addToCart() {
    this.router.navigateByUrl('/index/carrello', { state: { item: this.itemToAdd } });
  }
}
