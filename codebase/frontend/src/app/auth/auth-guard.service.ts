import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  constructor(private authService: AuthService, private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    let isLoggedIn = false;
    this.authService.isLoggedIn().then(res => {
      isLoggedIn = res;
      if (!isLoggedIn) {
        this.router.navigate(['/login']);
      }
    });
    return isLoggedIn;
  }

}
