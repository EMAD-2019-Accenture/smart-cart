import { Carrello } from '../shared/model/carrello';
import { Articolo } from '../shared/model/articolo';

export class CarrelloService {

  constructor() {

  }

  public makeCarrello(): Carrello {
    const carrello = new Carrello();
    let articoli: Array<Articolo>;
    // Toy da modificare
    articoli = [
      {
        id: '00',
        nome: 'Mela Coop',
        prezzo: 50,
        info: 'ciao'
      },
      {
        id: '01',
        nome: 'Pera Coop',
        prezzo: 100,
        info: 'peppe'
      }
    ];
    carrello.setArticoli(articoli);
    carrello.setActive(false);
    return carrello;
  }

  public activateCarrello(carrello: Carrello) {
    // Aggiornare stato persistente
    carrello.setActive(true);
  }

  public deactivateCarrello(carrello: Carrello) {
    // Aggiornare stato persistente
    carrello.setActive(false);
  }

  public deleteItem(carrello: Carrello, index: number) {
    // Aggiornare stato persistente
    carrello.getArticoli().splice(index, 1);
  }

  public increaseItem(carrello: Carrello, index: number) {
    // Aggiornare stato persstente
    // Aumentare quantitò di Voce Carrello
  }

  public decreaseItem(carrello: Carrello, index: number) {
    // Aggiornare stato persstente
    // Aumentare quantitò di Voce Carrello
  }
}
