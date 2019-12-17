import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs/operators';
import { Observable, BehaviorSubject } from 'rxjs';

import { Storage } from '@ionic/storage';
import { User } from './model/user';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  AUTH_SERVER_ADDRESS = 'http://localhost:8080/api/authenticate';
  private authSubject = new BehaviorSubject(false);
  private jwtHelper: JwtHelperService;

  constructor(private httpClient: HttpClient, private storage: Storage) { 
    this.jwtHelper = new JwtHelperService();
  }

  login(user: User) {
    return this.httpClient.post(`${this.AUTH_SERVER_ADDRESS}`, user).
      pipe(tap(async (res: any) => {

        if (res.id_token) {
          await this.storage.set('ACCESS_TOKEN', res.id_token);
          console.log('Set token', res.id_token);
          this.authSubject.next(true);
        }
      })
    ).toPromise();
  }

  async logout() {
    await this.storage.remove('ACCESS_TOKEN');
    this.authSubject.next(false);
  }

  async isLoggedIn() {
    const rawToken = await this.storage.get('ACCESS_TOKEN');
    console.log('Raw Token', rawToken);
    const isExpired = this.jwtHelper.isTokenExpired(rawToken);
    console.log('Is token expired?', isExpired);
    return !isExpired;
  }

  // TODO CHANGE
  public isLoggedIn2(): Promise<string> {
    return this.storage.get('ACCESS_TOKEN');
  }
}
