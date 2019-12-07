import { Cart } from '../shared/model/cart';
import { CartItem, ICartItem } from '../shared/model/cart-item';
import { isDevMode } from '@angular/core';

// tslint:disable: max-line-length
export class CarrelloService {

  constructor() {

  }

  // DEBUG Helper function
  private mockCart() {
    const cart = new Cart();
    const items: Array<CartItem> = new Array<CartItem>();
    if (isDevMode()) {
      const itemJs: ICartItem = {
        id: 1,
        product: {
          id: 1252,
          barcode: '8001120783806',
          name: ' Robiola 100 g',
          description: 'Senza conservanti, 100g',
          price: 1.3,
          brand: 'COOP',
          amount: null,
          imageUrl: null,
          source: 'Latte: Italia',
          ingredients: ' pastorizzato, , Sale, Caglio',
          conservation: 'Prodotto confezionato in atmosfera protettiva., Conservare in frigorifero tra 0 °C e +4 °C.',
          preparation: '',
          nutrients: 'Energia: 1298 kJ, Energia: 314 kcal, Grassi: 31 g, di cui acidi grassi saturi: 22 g, Carboidrati: 2,4 g, di cui zuccheri: 2,3 g, Proteine: 6,4 g, Sale: 0,67g',
          categories: null,
          discount: null,
          percentDiscount: null,
          allergens: [
            {
              id: 1051,
              name: 'Latte',
              description: 'Latte e prodotti derivati (compreso lattosio)',
              imageUrl: null,
              users: null
            }
          ],
          transaction: null,
          kForN: null
        },
        quantity: 10
      };
      const item: CartItem = new CartItem(itemJs);
      items.push(item);
    }
    cart.setActive(true);
    cart.setItems(items);
    return cart;
  }

  public makeCart(): Cart {
    return this.mockCart();
  }

  public activateCart(cart: Cart) {
    cart.setActive(true);
  }

  public deactivateCart(cart: Cart) {
    cart.setActive(false);
  }

  public getTotalPrice(cart: Cart) {
    if (cart.getItems().length > 0) {
      return cart.getItems()
        .map(item => item.getProduct().getPrice() * item.getQuantity())
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
}
