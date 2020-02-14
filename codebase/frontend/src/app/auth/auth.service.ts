import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Storage } from '@ionic/storage';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  // TODO Make it use HttpCommonService
  // AUTH_SERVER_ADDRESS = 'http://localhost:8080/api/authenticate';
  AUTH_SERVER_ADDRESS = 'https://smart-cart-acenture.herokuapp.com/api/authenticate';
  private jwtHelper: JwtHelperService;

  constructor(private httpClient: HttpClient, private storage: Storage) {
    this.jwtHelper = new JwtHelperService();
  }

  async login(username: string, password: string) {
    let result = false;
    try {
      const response: any = await this.httpClient.post(this.AUTH_SERVER_ADDRESS, { password, username }).toPromise();
      if (response.id_token) {
        await this.storage.set('ACCESS_TOKEN', response.id_token);
        result = true;
      }
    } catch (error) {
      console.log(error);
      result = false;
    }
    return result;

    /*pipe(tap(async (res: any) => {

      if (res.id_token) {

        const token = await
        console.log('Get token in login', this.getAuthToken());
      }
    })
  ).toPromise();*/
  }

  async logout() {
    await this.storage.remove('ACCESS_TOKEN');
  }

  async isLoggedIn(): Promise<boolean> {
    const rawToken = await this.getAuthToken();
    const isExpired = this.jwtHelper.isTokenExpired(rawToken);
    console.log('Is token expired?', isExpired);
    return !isExpired;
  }

  getAuthToken(): Promise<string> {
    return this.storage.get('ACCESS_TOKEN') as Promise<string>;
  }

  async getCurrentUsername() {
    const token = await this.getAuthToken();
    if (!token) {
      return null;
    }
    const username = (this.jwtHelper.decodeToken(token).sub as string);
    console.log(username);
    return username;
  }

}
