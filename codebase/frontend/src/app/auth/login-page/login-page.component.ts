import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoadingController } from '@ionic/angular';
import { ToastService } from '../../core/services/toast.service';
import { AuthService } from '../auth.service';
import { LoadingService } from 'src/app/core/services/loading.service';

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
    let message = '';
    let color = '';
    const loading: HTMLIonLoadingElement = await this.loadingService.presentWait('Attendi...', true);
    const auth = await this.authService.login(this.username, this.password);
    loading.dismiss();
    if (auth) {
      message = 'Login effettuato!';
      color = 'success';
      await this.router.navigateByUrl('/');
      this.username = '';
      this.password = '';
    } else {
      message = 'Login fallito!';
      color = 'danger';
    }
    this.toastService.presentToast(message, 2000, true, color, true);
  }
}
