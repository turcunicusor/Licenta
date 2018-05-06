import {Injectable, NgModule} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams} from '@angular/common/http';
import {AppSettingsDirective} from '../app-settings.directive';
import {Observable} from 'rxjs/Observable';
import {Router} from '@angular/router';

export class User {
  firstName: string;
  lastName: string;
  email: string;
}

interface Dictionary {
  [index: string]: string;
}

export class Device {
  public id: string;
  public ip: string;
  public port: string;
  public name: string;
  public type: string;
  public params: Dictionary;

  constructor() {
    this.name = 'DEFAULT';
    this.type = 'led';
    this.id = 'randomid';
    this.ip = '127.0.0.1';
    this.port = '8000';
    this.params = {'intensitate': '80%', 'temperatura': '12grade'};
  }
}

@Injectable()
export class Utils {
  keys(param: Dictionary): Array<string> {
    return Object.keys(param);
  }

  keyindex(param: Dictionary, key: string): any {
    return Object.keys(param).indexOf(key);
  }
}

@Injectable()
export class BackendService {
  public deviceTypes: String[];
  public isLoggedIn;
  public timeout = 1000;
  private server_url = 'http://e6b5bcd3.ngrok.io';
  private token;
  private email;
  private auth_header;
  private def_header;

  constructor(private _http: HttpClient, public router: Router) {
    this.deviceTypes = [];
    this.deviceTypes.push('led', 'rbga led', 'doorlock', 'central heating', 'temperature sensor');
    this.token = 'Bearer ';
    this.email = null;
    this.isLoggedIn = false;
    this.auth_header = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer '
    });
    this.def_header = new HttpHeaders({
      'Content-Type': 'application/json'
    });
  }

  public setToken(token: string) {
    this.token = token;
    this.isLoggedIn = true;
    this.auth_header = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': token
    });
  }

  redirect(page = '') {
    this.router.navigate(['/' + page]);
  }

  redirectWithParam(page = '', param = '') {
    this.router.navigate(['/' + page, param]);
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
    this.email = json['email'];
    return this.post('/login', json, this.def_header);
  }

  public logout(): Observable<any> {
    return this.post('/user/logout', {email: this.email});
  }

  public logoutSucess() {
    console.log('logout sucess');
    this.token = 'Bearer ';
    this.auth_header = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer '
    });
    this.isLoggedIn = false;
  }

  public register(json): Observable<any> {
    return this.post('/register', json, this.def_header);
  }

  public getAllUsers(): Observable<any> {
    return this.get('/user/all');
  }

  public profile(): Observable<any> {
    const url = '/user/profile';
    const params = new HttpParams().set('email', this.email);
    return this._http.get<User>(this.server_url + url, {headers: this.auth_header, params: params});
  }

  public updateProfile(json): Observable<any> {
    return this.post('/user/profile', json);
  }

  public updateEmail(email) {
    this.email = email;
  }

  public getAllDevices(): Observable<Device[]> {
    return this._http.get<Device[]>(this.server_url + '/device/all', {headers: this.auth_header});
  }

  public addNewDevice(json) {
    return this.post('/device', json);
  }

  public getDevice(id: String): Observable<Device> {
    return this._http.get<Device>(this.server_url + '/device?device=' + id, {headers: this.auth_header});
  }

  public deleteDevice(id: String) {
    return this._http.delete(this.server_url + '/device?device=' + id, {headers: this.auth_header});
  }

  public updateDevice(json, id) {
    return this._http.put(this.server_url + '/device?device=' + id, JSON.stringify(json), {headers: this.auth_header});
  }
}
