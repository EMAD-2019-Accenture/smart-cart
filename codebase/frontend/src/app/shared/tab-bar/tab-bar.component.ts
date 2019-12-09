import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/auth/auth.service';
import { from, Observer, Observable } from 'rxjs';

@Component({
  selector: 'app-tab-bar',
  templateUrl: './tab-bar.component.html',
  styleUrls: ['./tab-bar.component.scss'],
})
export class TabBarComponent implements OnInit {

  visible: boolean;

  constructor(private authService: AuthService) {
    this.visible = false;
    const observable: Observable<string> = from(authService.isLoggedIn2());
    observable.subscribe({
      next: (token: string) => this.hide(token)
    });
  }

  ngOnInit() { }

  // TODO Fix for LoginPage
  private hide(token: string) {
    console.log(token);
    if (token) {
      this.visible = true;
    } else {
      this.visible = false;
    }
  }

}
