import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from '../auth/auth.service';

// tslint:disable: align
export class HttpCommonService {
  private host = 'http://localhost:8100';

  constructor(private http: HttpClient, private authService: AuthService) { }

  private makeHttpOptions(token: string) {
    return {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + token,
        'Access-Control-Allow-Origin': this.host,
      })
    };
  }

  public async getRequest(path: string): Promise<any> {
    const token: string = await this.authService.getAuthToken();
    const httpOptions = this.makeHttpOptions(token);
    return this.http.get(path, httpOptions).toPromise();
  }

  public async putRequest(path: string, httpBody: string): Promise<any> {
    const token: string = await this.authService.getAuthToken();
    const httpOptions = this.makeHttpOptions(token);
    return this.http.put(path, httpBody, httpOptions).toPromise();
  }
}
