import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ArticoloService } from '../articolo.service';
import { Product } from '../model/product';

@Component({
  selector: 'app-articolo-page',
  templateUrl: './articolo-page.component.html',
  styleUrls: ['./articolo-page.component.scss'],
})
export class ArticoloPageComponent implements OnInit {

  product: Product;

  constructor(private articoloService: ArticoloService, private route: ActivatedRoute) {
    // this.articoloService.getProduct(this.route.snapshot.params.id);
  }

  ngOnInit() { }

}
