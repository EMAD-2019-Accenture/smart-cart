import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
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
    private toastNotificationService: ToastService,
    private router: Router) { }

  ngOnInit() { }

  public async login() {
    let message = '';
    let color = '';
    const auth = await this.authService.login(this.username, this.password);
    if (auth) {
      message = 'Login effettuato!';
      color = 'success';
      this.username = '';
      this.password = '';
      this.router.navigateByUrl('/');
    } else {
      message = 'Login fallito!';
      color = 'danger';
    }
    this.toastNotificationService.presentToast(message, 2000, true, color, true);
  }
}
