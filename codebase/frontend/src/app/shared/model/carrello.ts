import { Articolo } from '../../shared/model/articolo';

export class Carrello {
    private active: boolean;
    // Lista di "voci carrello" e non di articoli
    private articoli: Array<Articolo>;

    public isActive(): boolean {
        return this.active;
    }

    public setActive(active: boolean) {
        this.active = active;
    }

    public getArticoli(): Array<Articolo> {
        return this.articoli;
    }

    public setArticoli(articoli: Array<Articolo>) {
        this.articoli = articoli;
    }
}

