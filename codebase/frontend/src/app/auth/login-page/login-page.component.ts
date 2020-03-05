import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginResponse } from 'src/app/core/model/login-response.enum';
import { LoadingService } from 'src/app/core/services/loading.service';
import { ToastService } from '../../core/services/toast.service';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss'],
})
// tslint:disable: align
export class LoginPageComponent implements OnInit {

  username: string;
  password: string;

  constructor(private authService: AuthService,
    private toastService: ToastService,
    private loadingService: LoadingService,
    private router: Router) { }

  ngOnInit() { }

  public async login() {
    const loading: HTMLIonLoadingElement = await this.loadingService.presentWait('Attendi...', true);
    const result: LoginResponse = await this.authService.login(this.username, this.password);
    loading.dismiss();
    if (result === LoginResponse.SUCCESS) {
      await this.router.navigateByUrl('/');
      this.username = '';
      this.password = '';
    } else {
      let message = '';
      switch (result) {
        case LoginResponse.BAD_REQUEST: {
          message = 'Errore: compila tutti i campi';
          break;
        }
        case LoginResponse.UNAUTHORIZED: {
          message = 'Errore: non sei autorizzato';
          break;
        }
        case LoginResponse.NO_NETWORK: {
          message = 'Errore: rete assente';
          break;
        }
      }
      this.toastService.presentToast(message, 2000, true, 'danger', true);
    }

  }
}
