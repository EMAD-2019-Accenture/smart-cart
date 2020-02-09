import { Injectable, isDevMode } from '@angular/core';
import { Cart } from '../core/model/cart';
import { CartItem, ICartItem } from '../core/model/cart-item';
import { HttpCommonService } from '../core/services/http-common.service';
import { ArticoloService } from '../core/services/articolo.service';

// tslint:disable:align
@Injectable({
  providedIn: 'root',
})
export class CarrelloService {
  private addTransactionPath = 'https://smart-cart-acenture.herokuapp.com/api/transactions/';

  constructor(private http: HttpCommonService,
    private articoloService: ArticoloService) { }

  public makeEmptyCart(): Cart {
    return new Cart();
  }

  public activateCart(cart: Cart) {
    cart.setActive(true);
  }

  public checkout(cart: Cart) {
    const productIds: number[] = cart.getItems().map(item => item.getId());
    const httpBody: string = JSON.stringify({
      productIds,
      date: new Date(),
      // TODO: Get logged user ID
      user: '1'
    });
    // TODO: Uncomment when it works
    // this.http.postRequest(this.addTransactionPath, httpBody).then(() => {
    // });
  }

  public getUnitFullPrice(cart: Cart, index: number): number {
    return this.articoloService.getUnitFullPrice(cart.getItems()[index].getProduct());
  }

  public getFullPrice(cart: Cart, index: number): number {
    return this.articoloService.getFullPrice(cart.getItems()[index]);
  }

  public getUnitDiscountedPrice(cart: Cart, index: number): number {
    return this.articoloService.getUnitDiscountedPrice(cart.getItems()[index].getProduct());
  }

  public getDiscountedPrice(cart: Cart, index: number): number {
    return this.articoloService.getDiscountedPrice(cart.getItems()[index]);
  }

  public getTotalFullPrice(cart: Cart) {
    if (cart.getItems().length > 0) {
      return cart.getItems()
        .map((_, index) => this.getFullPrice(cart, index))
        .reduce((total, price) => total + price);
    } else {
      return 0;
    }
  }

  public getTotalDiscountedPrice(cart: Cart) {
    if (cart.getItems().length > 0) {
      return cart.getItems()
        .map((_, index) => this.getDiscountedPrice(cart, index))
        .reduce((total, price) => total + price);
    } else {
      return 0;
    }
  }

  public getTotalQuantity(cart: Cart) {
    if (cart.getItems().length > 0) {
      return cart.getItems()
        .map(item => item.getQuantity())
        .reduce((total, quantity) => total + quantity);
    } else {
      return 0;
    }
  }

  public addItem(cart: Cart, newItem: ICartItem) {
    const cartItem: CartItem = cart.getItems().find(value => value.getProduct().getId() === newItem.product.id);
    if (cartItem) {
      cartItem.setQuantity(cartItem.getQuantity() + newItem.quantity);
    } else {
      cart.getItems().push(new CartItem(newItem));
    }
  }

  public deleteItem(cart: Cart, index: number) {
    cart.getItems().splice(index, 1);
  }

  public increaseItem(cart: Cart, index: number) {
    const cartItem = cart.getItems()[index];
    cartItem.setQuantity(cartItem.getQuantity() + 1);
  }

  public decreaseItem(cart: Cart, index: number) {
    const cartItem = cart.getItems()[index];
    if (cartItem.getQuantity() > 1) {
      cartItem.setQuantity(cartItem.getQuantity() - 1);
    } else {
      if (cartItem.getQuantity() === 1) {
        this.deleteItem(cart, index);
      } else {
        // Non puoi essere qui!! Errore!!!!!!
      }
    }
  }

  /**
   * Creates a fake cart with a single item. Only in dev mode
   */
  // tslint:disable: max-line-length
  private fakeCart() {
    const cart = new Cart();
    const items: Array<CartItem> = new Array<CartItem>();
    cart.setItems(items);
    const itemJs: ICartItem = {
      id: 1,
      product: {
        id: 6,
        barcode: '8001120783806',
        name: ' Robiola 100 g',
        description: 'Senza conservanti, 100g',
        price: 1.3,
        brand: 'COOP',
        amount: null,
        imageUrl: 'https://cdn.easycoop.com/media/catalog/product/cache/f03df66a89eb7a38621fcea70cf640b3/r/o/robiola_100_g_6875689_40704_1380580_1.jpeg',
        source: 'Latte: Italia',
        ingredients: ' pastorizzato, , Sale, Caglio',
        conservation: 'Prodotto confezionato in atmosfera protettiva., Conservare in frigorifero tra 0 °C e +4 °C.',
        preparation: '',
        nutrients: 'Energia: 1298 kJ, Energia: 314 kcal, Grassi: 31 g, di cui acidi grassi saturi: 22 g, Carboidrati: 2,4 g, di cui zuccheri: 2,3 g, Proteine: 6,4 g, Sale: 0,67g',
        category: {
          id: 1,
          name: 'Alimentari'
        },
        discount: null,
        percentDiscount: null,
        allergens: [
          {
            id: 2,
            name: 'Latte',
            description: 'Latte e prodotti derivati (compreso lattosio)',
            imageUrl: null
          }
        ],
        kForN: null
      },
      quantity: 10
    };
    const item: CartItem = new CartItem(itemJs);
    items.push(item);
    cart.setActive(true);
    return cart;
  }
}
