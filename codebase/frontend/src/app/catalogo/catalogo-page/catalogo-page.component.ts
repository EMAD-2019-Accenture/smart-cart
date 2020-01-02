import { Component, OnInit } from '@angular/core';
import { Category } from 'src/app/shared/model/category';
import { Product } from 'src/app/shared/model/product';
import { CatalogoService } from '../catalogo.service';

@Component({
  selector: 'app-catalogo-page',
  templateUrl: './catalogo-page.component.html',
  styleUrls: ['./catalogo-page.component.scss'],
})
export class CatalogoPageComponent implements OnInit {

  allProducts: Array<Product>;
  filteredProducts: Array<Product>;
  categories: Array<Category>;

  constructor(private catalogoService: CatalogoService) {
    this.allProducts = new Array<Product>();
    this.filteredProducts = new Array<Product>();
    this.categories = new Array<Category>();
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

  public filter(searchBar: HTMLInputElement, dropdownCategory: HTMLInputElement, discountCheck: HTMLInputElement): void {
    this.filteredProducts = this.allProducts/*.sort((a, b) => {
      return a.getName().localeCompare(b.getName());
    })*/;
    // Category filter
    const selectedIndex: number = Number(dropdownCategory.value);
    if (selectedIndex !== 0) {
      const category: Category = this.categories[selectedIndex - 1];
      this.filteredProducts = this.filteredProducts.filter(product => product.getCategory().getId() === category.getId());
    }
    // Keyword filter
    const regex = new RegExp('^.*(' + searchBar.value + ').*$', 'i');
    this.filteredProducts = this.filteredProducts.filter(product => regex.test(product.getName()));
    // Discount filter
    if (discountCheck.checked) {
      this.filteredProducts = this.filteredProducts.filter(product => {
        const discount: string = JSON.stringify(product.getDiscount());
        // const percentDiscount: string = JSON.stringify(product.getPercentDiscount());
        // const kForN: string = JSON.stringify(product.getKForN());
        return discount !== '{}' /*|| percentDiscount !== '{}' || kForN !== '{}'*/;
      });
    }
  }
}
