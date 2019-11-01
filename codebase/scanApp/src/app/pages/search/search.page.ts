import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-search',
  templateUrl: './search.page.html',
  styleUrls: ['./search.page.scss'],
})
export class SearchPage implements OnInit {

  public categories = [];
  public filteredCategories = [];
  public searchTerm : String;

  constructor() { 
    this.categories = [
      "Animals & Pet Supplies",
      "Apparel & Accessories",
      "Arts & Entertainment",
      "Baby & Toddler",
      "Business",
      "Books",
      "Cameras",
      "Food",
      "Drugs",
    ];
    this.searchTerm = "";
  }

  ngOnInit() {    
    this.setFilteredItems();
  }

  public setFilteredItems(){
    this.filteredCategories = this.categories.filter(item => {      
      if(item.toLowerCase().indexOf(this.searchTerm.toLowerCase()) >= 0){
        return item;
      }
      
    });
  }

}
