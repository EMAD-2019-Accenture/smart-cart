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
      this.filteredProducts = this.allProducts;
    });
  }

  public filter(searchBar: HTMLInputElement, dropdownCategory: HTMLInputElement): void {
    this.filteredProducts = this.allProducts;
    const selectedIndex: number = Number(dropdownCategory.value);
    if (selectedIndex !== 0) {
      const category: Category = this.categories[selectedIndex - 1];
      this.filteredProducts = this.filteredProducts.filter(product =>
        product.getCategory().getId() === category.getId()
      );
    }
    const keyword: string = searchBar.value;
    this.filteredProducts = this.filteredProducts.filter(product => {
      const regex = new RegExp('^.*(' + keyword + ').*$', 'i');
      return regex.test(product.getName());
    });
  }
}
