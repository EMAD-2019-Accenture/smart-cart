import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from '../../auth/auth.service';
import { Injectable } from '@angular/core';

// tslint:disable: align
@Injectable({
  providedIn: 'root',
})
export class HttpCommonService {

  constructor(private http: HttpClient, private authService: AuthService) { }

  private makeHttpOptions(token: string) {
    return {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + token,
        'Content-Type': 'application/json'
      })
    };
  }

  public async getRequest(path: string): Promise<any> {
    const token: string = await this.authService.getAuthToken();
    const httpOptions = this.makeHttpOptions(token);
    return this.http.get(path, httpOptions).toPromise();
  }

  public async postRequest(path: string, httpBody: string): Promise<any> {
    const token: string = await this.authService.getAuthToken();
    const httpOptions = this.makeHttpOptions(token);
    return this.http.post(path, httpBody, httpOptions).toPromise();
  }

  public async putRequest(path: string, httpBody: string): Promise<any> {
    const token: string = await this.authService.getAuthToken();
    const httpOptions = this.makeHttpOptions(token);
    return this.http.put(path, httpBody, httpOptions).toPromise();
  }
}
