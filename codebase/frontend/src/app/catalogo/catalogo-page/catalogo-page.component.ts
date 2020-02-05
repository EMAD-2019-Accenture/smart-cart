import { Component, OnInit } from '@angular/core';
import { PopoverController } from '@ionic/angular';
import { Category } from 'src/app/shared/model/category';
import { Product } from 'src/app/shared/model/product';
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
    private popoverController: PopoverController) {
    this.allProducts = new Array<Product>();
    this.filteredProducts = new Array<Product>();
    this.categories = new Array<Category>();
    this.keyword = '';
    this.selectedCategory = 0;
    this.discountCheck = false;
  }

  ngOnInit() {
    this.catalogoService.getCategories().then((value) => {
      this.categories = value.map(category => new Category(category));
    });
    this.catalogoService.getProducts().then(value => {
      this.allProducts = value.map(product => new Product(product));
      this.allProducts.sort((a, b) => {
        return a.getName().localeCompare(b.getName());
      });
      this.filteredProducts = this.allProducts;
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
