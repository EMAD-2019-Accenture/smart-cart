import { Component, OnInit } from '@angular/core';
import { User } from '../model/user';
import { AuthService } from '../auth.service';
import {ToastNotificationService} from '../../shared/toast/toast-notification.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss'],
})
export class LoginPageComponent implements OnInit {

  model: User = { password: '', username: '' };

  constructor(private authService: AuthService, private toastNotificationService: ToastNotificationService, private router: Router) { }

  ngOnInit() {
    /* Check Login through AuthService */
  }

  login() {
    let toast = this.toastNotificationService;
    let router = this.router;

    this.authService.login(this.model)
      .subscribe({
        next(logInfo) {
          let message = "Login effettuato!";
          let color = "success";
          toast.presentToast(message, color);
          router.navigate(['/carrello'])
        },
        error(err) {
          let message = "Login fallito!";
          let color = "danger";
          toast.presentToast(message, color);   
        }
      })
    }
}
