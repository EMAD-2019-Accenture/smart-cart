import { Component, OnInit } from '@angular/core';
import { User } from '../model/user';
import { AuthService } from '../auth.service';
import {ToastNotificationService} from '../../shared/toast/toast-notification.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss'],
})
export class LoginPageComponent implements OnInit {

  model: User = { password: '', username: '' };

  constructor(private authService: AuthService, public toastNotificationService: ToastNotificationService) { }

  ngOnInit() {
    /* Check Login through AuthService */
  }

  login() {
    let toast = this.toastNotificationService;
    this.authService.login(this.model)
      .subscribe({
        next(logInfo) {
          let message = "Login effettuato!";
          let color = "success";
          toast.presentToast(message, color); 
        },
        error(err) {
          let message = "Login fallito!";
          let color = "danger";
          toast.presentToast(message, color);   
        }
      })
    }
}
