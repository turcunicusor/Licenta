import {Injectable, NgModule} from '@angular/core';
import {HttpClient, HttpClientModule, HttpHeaders} from '@angular/common/http';
import {AppSettingsDirective} from '../app-settings.directive';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class BackendService {
  private token;
  private def_header = new HttpHeaders({
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*'
  });
  private auth_header;

  constructor(private _http: HttpClient) {
    this.auth_header = new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Authorization': 'Bearer '
    });
  }

  public setToken(token: string) {
    this.token = token;
    this.auth_header = new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Authorization': 'Bearer ' + token
    });
  }

  public get(url): Observable<any> {
    const httpheader = new HttpHeaders({
      'Access-Control-Expose-Headers': 'Authorization',
      'Authorization': 'AUTHORIZATION: Bearer ' + this.token
    });
    return this._http.get<any[]>(AppSettingsDirective.server_url + url,
      {headers: httpheader});
  }

  public post(url, json, headers = this.auth_header): Observable<any> {
    return this._http.post<any>(AppSettingsDirective.server_url + url, JSON.stringify(json),
      {headers: headers, observe: 'response'});
  }

  public login(json): Observable<any> {
    return this.post('/login', json, this.def_header);
  }

  public register(json): Observable<any> {
    return this.post('/register', json, this.def_header);
  }

  public getAllUsers(): Observable<any> {
    return this.get('/users/all');
  }

  // private username: string;
  //
  // constructor(private http: Http) {
  // }
  //
  // setUsername(username: string) {
  //   this.username = username;
  // }
  //
  // getUsername() {
  //   return this.username;
  // }
  //
  // logoutUser(token: string) {
  //   const header = new Headers();
  //   header.append('Authorization', token);
  //
  //   const json = JSON.stringify({});
  //   return this.http.post('http://localhost:4500/user/logout', json, {headers: header}).map(res => res.json());
  // }
  //
  // getUsersByStringMatch(token: string, stringMatch : string) {
  //   const header = new Headers();
  //   header.append('Authorization', token);
  //
  //   return this.http.get('http://localhost:4500//f
  // indUsers/string_match="' + stringMatch + '"', {headers: header}).map(res => res.json());
  // }
}
