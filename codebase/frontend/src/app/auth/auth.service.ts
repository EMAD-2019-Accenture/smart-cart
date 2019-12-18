import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs/operators';
import { Observable, BehaviorSubject } from 'rxjs';

import { Storage } from '@ionic/storage';
import { User } from '../shared/model/user';
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

  login(username: string, password: string) {
    return this.httpClient.post(`${this.AUTH_SERVER_ADDRESS}`, {password, username}).
      pipe(tap(async (res: any) => {

        if (res.id_token) {
          await this.storage.set('ACCESS_TOKEN', res.id_token);
          this.authSubject.next(true);
        }
      })
    ).toPromise();
  }

  async logout() {
    await this.storage.remove('ACCESS_TOKEN');
    this.authSubject.next(false);
  }

  async isLoggedIn(): Promise<boolean> {
    const rawToken = await this.getAuthToken();
    const isExpired = this.jwtHelper.isTokenExpired(rawToken);
    console.log('Is token expired?', isExpired);
    this.getCurrentUsername();
    return !isExpired;
  }

  getAuthToken(): Promise<string> {
    return this.storage.get('ACCESS_TOKEN') as Promise<string>;
  }

  async getCurrentUsername() {
    const token = await this.getAuthToken();
    const username = (this.jwtHelper.decodeToken(token).sub as string);
    console.log(username);
    return username;
  }

}
