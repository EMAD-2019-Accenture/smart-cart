import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { LoadingController } from '@ionic/angular';
import { AuthService } from 'src/app/auth/auth.service';
import { AlertService } from 'src/app/core/services/alert.service';
import { ToastService } from 'src/app/core/services/toast.service';
import { Customer, ICustomer } from 'src/app/core/model/customer';
import { PreferenzeService } from '../preferenze.service';
import { LoadingService } from 'src/app/core/services/loading.service';

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
    private loadingService: LoadingService,
    private router: Router,
    private activatedRoute: ActivatedRoute) {
    this.customer = null;
    // this.customer = this.preferenceService.makeEmptyCustomer();
  }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(() =>
      this.getCustomer().then(response => {
        if (response !== null) {
          this.customer = new Customer(response);
          this.veganToggle = this.customer.isVegan();
          this.vegetarianToggle = this.customer.isVegetarian();
          this.celiacToggle = this.customer.isCeliac();
        } else {
          this.customer = null;
          this.veganToggle = false;
          this.vegetarianToggle = false;
          this.celiacToggle = false;
        }
      }));
  }

  private async getCustomer() {
    const loading: HTMLIonLoadingElement = await this.loadingService.presentWait('Attendi...', true);
    return await this.preferenceService.getCustomer()
      .then(value => value)
      .catch(reason => {
        const message = 'Errore: rete assente';
        this.toastService.presentToast(message, 2000, true, 'danger', true);
        console.log('Couldn\'t get user ' + reason);
        return null;
      })
      .finally(() => loading.dismiss());
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
    if (this.customer != null) {
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
    } else {
      const message = 'Impossibile salvare: rete assente';
      this.toastService.presentToast(message, 2000, true, 'danger', true);
    }
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
