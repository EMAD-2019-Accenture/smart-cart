import { Component, OnInit } from '@angular/core';
import { Customer } from 'src/app/shared/model/customer';
import { ToastNotificationService } from 'src/app/shared/toast/toast-notification.service';
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

  constructor(private preferenceService: PreferenzeService,
    private toastNotificationService: ToastNotificationService) {
    this.customer = this.preferenceService.makeEmptyCustomer();
  }

  ngOnInit() {
    this.getCustomer().then((response) => {
      this.customer = new Customer(response);
      this.veganToggle = this.customer.isVegan();
      this.vegetarianToggle = this.customer.isVegetarian();
      this.celiacToggle = this.customer.isCeliac();
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
    let message: string;
    let color: string;
    this.preferenceService.update(this.customer).then((value) => {
      message = 'Preferenze aggiornate';
      color = 'success';
    }).catch((reason) => {
      message = 'Errore: aggiornamento fallito';
      color = 'danger';
    }).finally(() => {
      this.toastNotificationService.toastController.dismiss().finally(() => {
        this.toastNotificationService.presentToast(message, color);
      });
    });
  }
}
