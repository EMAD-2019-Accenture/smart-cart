import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { AuthService } from 'src/app/auth/auth.service';
import { Customer } from 'src/app/shared/model/customer';
import { PreferenzeService } from '../preferenze.service';

@Component({
  selector: 'app-preferenze-page',
  templateUrl: './preferenze-page.component.html',
  styleUrls: ['./preferenze-page.component.scss'],
})
// tslint:disable: align
export class PreferenzePageComponent implements OnInit {

  customer: Customer;
  veganToggle: boolean;
  vegetarianToggle: boolean;
  celiacToggle: boolean;

  constructor(private preferenceService: PreferenzeService, private authService: AuthService) {
    this.customer = this.preferenceService.makeEmptyCustomer();
    this.veganToggle = this.customer.isVegan();
    this.vegetarianToggle = this.customer.isVegetarian();
    this.celiacToggle = this.customer.isCeliac();
  }

  ngOnInit() {
    this.getCustomer().then((response) => {
      this.customer = new Customer(response);
    });
  }

  private getCustomer() {
    return this.preferenceService.getCustomer();
  }

  public onVegan() {
    this.customer.setVegan(this.veganToggle);
  }

  public onVegetarian() {
    this.customer.setVegetarian(this.vegetarianToggle);
  }

  public onCeliac() {
    this.customer.setCeliac(this.celiacToggle);
  }

  public save() {
    this.preferenceService.update(this.customer);
  }
}
