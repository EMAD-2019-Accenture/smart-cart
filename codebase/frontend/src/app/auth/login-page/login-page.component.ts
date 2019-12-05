import { Component, OnInit } from '@angular/core';
import { User } from '../model/user';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss'],
})
export class LoginPageComponent implements OnInit {

  model: User = {password: '', username: ''};

  constructor(private authService: AuthService) { }

  ngOnInit() {
    /* Check Login through AuthService */
  }

  login() {
    console.log('Login with server');
    this.authService.login(this.model).subscribe(obs => {
      console.log(obs);
    });
  }

}
