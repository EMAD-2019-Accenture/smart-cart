import { Cart } from '../shared/model/cart';
import { CartItem, ICartItem } from '../shared/model/cart-item';

// tslint:disable: max-line-length
export class CarrelloService {

  constructor() {

  }

  public makeCarrello(): Cart {
    const cart = new Cart();
    const items: Array<CartItem> = new Array<CartItem>();

    // DEBUG ONLY - Must be removed
    const itemJs: ICartItem = {
      product: {
        barcode: '2148693000000',
        name: ' Spiedini rustici di suino Italiano',
        description: 'Origine, Tracciabilità totale - Filiera di qualità, - Senza glutine, - Alimentazione no OGM*, - Confezionato in atmosfera protettiva, *Relativa alla carne di suino',
        price: 20,
        brand: 'ORIGINE',
        amount: 100,
        imageUrl: '',
        source: '',
        ingredients: 'Salsiccia di suino 45% (carne di suino, acqua, sale iodato (sale, iodato di potassio 0,007%), pepe, zucchero, destrosio da mais, aromi naturali, antiossidante: acido ascorbico, ascorbato di sodio; correttore di acidità: citrato di sodio), Carne di suino 37%, Peperone 16%',
        conservation: 'CONSERVARE IN FRIGORIFERO DA 0°C A +4°C',
        preparation: 'Da consumare previa cottura., Suggerimenti per l\'uso, - Piastra pronti in 15 minuti., - Padella pronti in 15 minuti., - Griglia pronti in 15 minuti., - Forno pronti in 15/20 minuti a 180°., I tempi di cottura sono indicativi. Controlla la cottura e regolati secondo il tuo gradimento.',
        nutrients: 'Energia: 573 kJ, Grassi: 7,5 g, di cui acidi grassi saturi: 3 g, Carboidrati: 0,9 g, di cui zuccheri: 0,6 g, Fibre: 0,4 g, Proteine: 17 g, Sale: 0,3 g',
        allergens: []
      },
      quantity: 10
    };
    const item: CartItem = new CartItem(itemJs);
    items.push(item);

    cart.setItems(items);
    cart.setActive(false);
    return cart;
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
    if (cartItem.getQuantity() < cartItem.getProduct().getAmount()) {
      cartItem.setQuantity(cartItem.getQuantity() + 1);
    } else {
      // Fine disponibilità! Mostrare avviso
    }
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
