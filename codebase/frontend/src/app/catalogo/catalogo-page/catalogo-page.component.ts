import { Component, OnInit } from '@angular/core';
import { Category } from 'src/app/shared/model/category';

@Component({
  selector: 'app-catalogo-page',
  templateUrl: './catalogo-page.component.html',
  styleUrls: ['./catalogo-page.component.scss'],
})
export class CatalogoPageComponent implements OnInit {

  categories: Array<Category>;

  constructor() {
    this.categories = new Array<Category>();
  }

  ngOnInit() { }

}
