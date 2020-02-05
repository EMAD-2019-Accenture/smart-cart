import { Component, OnInit } from '@angular/core';
import { PopoverController } from '@ionic/angular';
import { Category } from 'src/app/core/model/category';
import { Product } from 'src/app/core/model/product';
import { LoadingService } from 'src/app/core/services/loading.service';
import { CatalogoService } from '../catalogo.service';
import { PopoverComponent } from '../popover/popover.component';

@Component({
  selector: 'app-catalogo-page',
  templateUrl: './catalogo-page.component.html',
  styleUrls: ['./catalogo-page.component.scss'],
})
// tslint:disable: align
export class CatalogoPageComponent implements OnInit {

  allProducts: Array<Product>;
  filteredProducts: Array<Product>;
  categories: Array<Category>;

  public keyword: string;
  public selectedCategory: number;
  public discountCheck: boolean;

  constructor(private catalogoService: CatalogoService,
    private popoverController: PopoverController,
    private loadingService: LoadingService) {
    this.allProducts = new Array<Product>();
    this.filteredProducts = new Array<Product>();
    this.categories = new Array<Category>();
    this.keyword = '';
    this.selectedCategory = 0;
    this.discountCheck = false;
  }

  ngOnInit() {
    this.getCategoriesAndCatalogue();
  }

  // TODO: Make it more readable
  private async getCategoriesAndCatalogue() {
    const loading: HTMLIonLoadingElement = await this.loadingService.presentWait('Attendi...', true);
    await this.catalogoService.getCategories()
      .then(categories => {
        this.categories = categories.map(category => new Category(category));
      })
      .catch(reason => {
        this.categories = null;
        console.log('Couldn\'t get categories ' + reason);
      });
    await this.catalogoService.getProducts()
      .then(products => {
        this.allProducts = products.map(product => new Product(product));
        this.allProducts.sort((a, b) => a.getName().localeCompare(b.getName()));
        this.filteredProducts = this.allProducts;
      })
      .catch(reason => {
        this.allProducts = null;
        this.filteredProducts = null;
        console.log('Couldn\'t get products ' + reason);
      })
      .finally(() => loading.dismiss());
  }

  public filter() {
    let products: Product[] = this.allProducts/*.sort((a, b) => {
      return a.getName().localeCompare(b.getName());
    })*/;
    products = this.catalogoService.filterByKeyword(products, this.keyword);
    products = this.catalogoService.filterByCategory(products, this.categories, this.selectedCategory);
    products = this.catalogoService.filterByDiscountCheck(products, this.discountCheck);
    this.filteredProducts = products;
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
}
