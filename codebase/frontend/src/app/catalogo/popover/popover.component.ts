import { Component, Input, OnInit } from '@angular/core';
import { PopoverController } from '@ionic/angular';
import { Category } from 'src/app/core/model/category';

@Component({
  selector: 'app-popover',
  templateUrl: './popover.component.html',
  styleUrls: ['./popover.component.scss'],
})
export class PopoverComponent implements OnInit {

  @Input() categories: Array<Category>;
  @Input() selectedCategory: number;

  constructor(private popoverController: PopoverController) { }

  ngOnInit() { }

  public sendCategory(index: number) {
    this.popoverController.dismiss(index);
  }

}
