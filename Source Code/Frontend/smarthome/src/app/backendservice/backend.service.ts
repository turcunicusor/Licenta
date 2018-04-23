import {Injectable, NgModule} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams} from '@angular/common/http';
import {AppSettingsDirective} from '../app-settings.directive';
import {Observable} from 'rxjs/Observable';
import {Router} from '@angular/router';

@Injectable()
export class BackendService {
  public isLoggedIn;
  private server_url = 'http://261b5748.ngrok.io';
  private token;
  private auth_header;
  private def_header = new HttpHeaders({
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*'
  });

  constructor(private _http: HttpClient, private router: Router) {
    this.token = 'Bearer ';
    this.auth_header = new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Authorization': 'Bearer '
    });
    this.isLoggedIn = false;
  }

  public setToken(token: string) {
    this.token = token;
    this.auth_header = new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Authorization': token
    });
    this.isLoggedIn = true;
  }

  redirect(page = '') {
    this.router.navigate(['/' + page]);
  }

  public logError(error: HttpErrorResponse) {
    console.log(error.error);
    console.log(error.name);
    console.log(error.message);
    console.log(error.status);
  }

  public handleError(err: HttpErrorResponse) {
    this.logError(err);
    if (err.status === 401) {
      return 'Invalid credentials. Please try again.';
    } else if (err.status === 403) {
      return 'You must login to perform this action.';
    } else if (err.statusText === 'Unknown Error') {
      return 'Service currently not available.';
    } else if (err.status !== 500) {
      return err.error;
    } else {
      return AppSettingsDirective.def_err_message;
    }
  }

  public get(url): Observable<any> {
    return this._http.get(this.server_url + url, {headers: this.auth_header});
  }

  public post(url, json, headers = this.auth_header): Observable<any> {
    return this._http.post<any>(this.server_url + url, JSON.stringify(json),
      {headers: headers, observe: 'response'});
  }

  public login(json): Observable<any> {
    return this.post('/login', json, this.def_header);
  }

  public logout(): Observable<any> {
    return this.post('/logout', '');
  }

  public logoutSucess() {
    this.token = 'Bearer ';
    this.auth_header = new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Authorization': 'Bearer '
    });
    this.isLoggedIn = false;
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
