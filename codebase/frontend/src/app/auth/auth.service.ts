import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs/operators';
import { Observable, BehaviorSubject } from 'rxjs';

import { Storage } from '@ionic/storage';
import { User } from './model/user';


export class AuthService {

  AUTH_SERVER_ADDRESS  =  'http://localhost:8080/api/authenticate';
  authSubject  =  new  BehaviorSubject(false);

  constructor(private  httpClient: HttpClient, private  storage: Storage) { }

  login(user: User): Observable<any> {
    return this.httpClient.post(`${this.AUTH_SERVER_ADDRESS}`, user).pipe(
      tap(async (res) => {
        console.log(res);

        if (res.id_token) {
          await this.storage.set('ACCESS_TOKEN', res.id_token);
          this.authSubject.next(true);
        }
      })
    );
  }

  async logout() {
    await this.storage.remove('ACCESS_TOKEN');
    this.authSubject.next(false);
  }

  isLoggedIn() {
    return this.authSubject.asObservable();
  }
}
