import {Injectable, NgModule} from '@angular/core';
import {HttpClient, HttpClientModule, HttpHeaders} from '@angular/common/http';
import {AppSettingsDirective} from '../app-settings.directive';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class BackendService {
  private username: string;
  private password: string;
  private confirmPassword: string;
  private email: string;

  constructor(private _http: HttpClient) {
  }

  public get(url) {
    return this._http.get(url, {responseType: 'json'});
  }

  public post(url, json, header = AppSettingsDirective.def_header): Observable<Object> {
    return this._http.post(url, json, {headers: header});
  }

  public get_food(url) {
    return this.get(url);
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
