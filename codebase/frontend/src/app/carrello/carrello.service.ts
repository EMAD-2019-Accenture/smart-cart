import { Cart } from '../shared/model/cart';
import { Product } from '../shared/model/product';
import { CartItem } from '../shared/model/cart-item';

export class CarrelloService {

  constructor() {

  }

  public makeCarrello(): Cart {
    const carrello = new Cart();
    let items: Array<CartItem>;
    items = new Array<CartItem>();
    // Aggiungere elementi di test for debugging
    carrello.setItems(items);
    carrello.setActive(false);
    return carrello;
  }

  public activateCarrello(carrello: Cart) {
    // Aggiornare stato persistente
    carrello.setActive(true);
  }

  public deactivateCarrello(carrello: Cart) {
    // Aggiornare stato persistente
    carrello.setActive(false);
  }

  public deleteItem(carrello: Cart, index: number) {
    // Aggiornare stato persistente
    carrello.getItems().splice(index, 1);
  }

  public increaseItem(carrello: Cart, index: number) {
    // Aggiornare stato persstente
    const cartItem = carrello.getItems()[index];
    // Check disponibilità con query
    cartItem.setQuantity(cartItem.getQuantity() + 1);
  }

  public decreaseItem(carrello: Cart, index: number) {
    // Aggiornare stato persstente
    const cartItem = carrello.getItems()[index];
    // Check se 1
    cartItem.setQuantity(cartItem.getQuantity() - 1);
  }
}
