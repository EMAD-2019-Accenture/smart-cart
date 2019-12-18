import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service';
import { ToastNotificationService } from '../../shared/toast/toast-notification.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss'],
})
export class LoginPageComponent implements OnInit {

  username: string;
  password: string;

  constructor(private authService: AuthService, private toastNotificationService: ToastNotificationService, private router: Router) { }

  ngOnInit() {

  }

  async login() {
    let message = '';
    let color = '';
    try {
      const auth = await this.authService.login(this.username, this.password);
      console.log(auth);

      message = 'Login effettuato!';
      color = 'success';
      this.router.navigateByUrl('/');

    } catch (error) {
      console.log(error);
      message = 'Login fallito!';
      color = 'danger';
    }
    this.toastNotificationService.presentToast(message, color);
  }
}
