import { Component, OnInit } from '@angular/core';
import { Category } from 'src/app/shared/model/category';
import { CatalogoService } from '../catalogo.service';

@Component({
  selector: 'app-catalogo-page',
  templateUrl: './catalogo-page.component.html',
  styleUrls: ['./catalogo-page.component.scss'],
})
export class CatalogoPageComponent implements OnInit {

  categories: Array<Category>;

  constructor(private catalogoService: CatalogoService) {
    this.categories = new Array<Category>();
  }

  ngOnInit() {
    this.catalogoService.getCategories().then((value) => {
      this.categories = value;
      console.log(this.categories);
    });
  }

}
