import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PopoverController } from '@ionic/angular';
import { Category } from 'src/app/core/model/category';
import { Product } from 'src/app/core/model/product';
import { LoadingService } from 'src/app/core/services/loading.service';
import { ToastService } from 'src/app/core/services/toast.service';
import { CatalogoService } from '../catalogo.service';
import { PopoverComponent } from '../popover/popover.component';

@Component({
  selector: 'app-catalogo-page',
  templateUrl: './catalogo-page.component.html',
  styleUrls: ['./catalogo-page.component.scss'],
})
// tslint:disable: align
export class CatalogoPageComponent implements OnInit {

  private static readonly NEW_ITEMS = 30;

  allProducts: Array<Product>;
  filteredProducts: Array<Product>;
  categories: Array<Category>;

  public keyword: string;
  public selectedCategory: number;
  public discountCheck: boolean;
  public shownItems: number;

  constructor(private catalogoService: CatalogoService,
    private popoverController: PopoverController,
    private loadingService: LoadingService,
    private toastService: ToastService,
    private activatedRoute: ActivatedRoute) {
    this.allProducts = new Array<Product>();
    this.filteredProducts = new Array<Product>();
    this.categories = new Array<Category>();
    this.keyword = '';
    this.selectedCategory = 0;
    this.discountCheck = false;
    this.restartShownItems();
  }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(() => this.getCategoriesAndCatalogue());
  }

  private restartShownItems() {
    this.shownItems = CatalogoPageComponent.NEW_ITEMS < this.filteredProducts.length ?
      CatalogoPageComponent.NEW_ITEMS : this.filteredProducts.length + 1;
  }

  private async getCategoriesAndCatalogue() {
    const loading: HTMLIonLoadingElement = await this.loadingService.presentWait('Attendi...', true);
    await this.getCategories();
    await this.getProducts();
    loading.dismiss();
  }

  private getCategories(): Promise<void> {
    return this.catalogoService.getCategories()
      .then(categories => {
        this.categories = categories.map(category => new Category(category));
        this.categories = this.categories.sort((a, b) => a.getName().localeCompare(b.getName()));
      })
      .catch(reason => {
        this.categories = null;
        console.log('Couldn\'t get categories ' + reason);
      });
  }

  private getProducts(): Promise<void> {
    return this.catalogoService.getProducts()
      .then(products => {
        this.allProducts = products.map(product => new Product(product));
        this.allProducts.sort((a, b) => a.getName().localeCompare(b.getName()));
        this.filteredProducts = this.allProducts;
        this.restartShownItems();
      })
      .catch(reason => {
        this.allProducts = null;
        this.filteredProducts = null;
        const message = 'Errore: rete assente';
        this.toastService.presentToast(message, 2000, true, 'danger', true);
        console.log('Couldn\'t get products ' + reason);
      });
  }

  public filter() {
    let products: Product[] = this.allProducts/*.sort((a, b) => {
      return a.getName().localeCompare(b.getName());
    })*/;
    products = this.catalogoService.filterByKeyword(products, this.keyword);
    products = this.catalogoService.filterByCategory(products, this.categories, this.selectedCategory);
    products = this.catalogoService.filterByDiscountCheck(products, this.discountCheck);
    this.filteredProducts = products;
    this.restartShownItems();
  }

  public async presentPopover(event: any) {
    const popover = await this.popoverController.create({
      component: PopoverComponent,
      componentProps: {
        categories: this.categories,
        selectedCategory: this.selectedCategory
      },
      event,
      translucent: true
    });
    popover.onWillDismiss().then(result => {
      if (result.role !== 'backdrop') {
        this.selectedCategory = result.data;
        this.filter();
      }
    });
    popover.present();
  }

  public loadProducts(event) {
    setTimeout(() => {
      this.showMoreItems();
      event.target.complete();
    }, 500);
  }

  private showMoreItems() {
    this.shownItems = this.shownItems + CatalogoPageComponent.NEW_ITEMS < this.filteredProducts.length ?
      this.shownItems + CatalogoPageComponent.NEW_ITEMS : this.filteredProducts.length + 1;
  }
}
