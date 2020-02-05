import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';
import { AlertService } from 'src/app/core/services/alert.service';
import { ToastService } from 'src/app/core/services/toast.service';
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

  constructor(private preferenceService: PreferenzeService,
    private authService: AuthService,
    private toastService: ToastService,
    private alertService: AlertService,
    private router: Router) {
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
    this.preferenceService.update(this.customer).then(() => {
      message = 'Preferenze aggiornate';
      color = 'success';
    }).catch(() => {
      message = 'Errore: aggiornamento fallito';
      color = 'danger';
    }).finally(() => {
      this.toastService.dismiss().finally(() => {
        this.toastService.presentToast(message, 2000, true, color, true);
      });
    });
  }

  public logout() {
    this.alertService.presentConfirm('Sei sicuro di procedere al logout?', [
      {
        text: 'Annulla',
        role: 'cancel',
      },
      {
        text: 'Conferma',
        handler: () => {
          this.authService.logout().then(() => {
            this.router.navigateByUrl('auth');
          });
        }
      }
    ]);
  }
}
