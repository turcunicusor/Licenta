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

interface DictionaryParm {
  [index: string]: string;
}

interface DictionaryAccParm {
  [index: string]: ParameterDescription;
}

export class ParameterDescription {
  public readOnly: boolean;
  public type: string;

  constructor() {
    this.readOnly = false;
    this.type = 'unknown';
  }
}

export class Device {
  public id: string;
  public ip: string;
  public port: string;
  public name: string;
  public type: string;
  public status: string;
  public isOpened: boolean;
  public params: DictionaryParm;
  public acceptedParams: DictionaryAccParm;

  constructor() {
    this.name = '';
    this.type = '';
    this.id = '';
    this.ip = '';
    this.port = '';
    this.status = '';
    this.params = {};
    this.acceptedParams = {};
  }
}

@Injectable()
export class Utils {
  keys(param: DictionaryParm): Array<string> {
    return Object.keys(param);
  }

  keyindex(param: DictionaryParm, key: string): any {
    return Object.keys(param).indexOf(key);
  }

  checkEmpty(param: any): boolean {
    return Object.keys(param).length === 0;
  }
}

@Injectable()
export class BackendService {
  public deviceTypes: string[];
  public isLoggedIn;
  public timeout = 1000;
  private server_url = 'http://72c89a08.ngrok.io';
  private token;
  private email;
  private auth_header;
  private def_header;

  constructor(private _http: HttpClient, public router: Router) {
    this.deviceTypes = [];
    this.deviceTypes.push('led', 'rbga led', 'doorlock', 'centralheating', 'temperature sensor');
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
    return this.post('/user/logout', '');
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
    return this.post('/register', JSON.stringify(json), this.def_header);
  }

  public profile(): Observable<any> {
    const url = '/user/profile';
    return this._http.get<User>(this.server_url + url, {headers: this.auth_header});
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

  public testConnectionDevice(id) {
    return this._http.put(this.server_url + '/device/testConnection?device=' + id, '', {headers: this.auth_header});
  }

  public connectDevice(id) {
    return this._http.put(this.server_url + '/device/connect?device=' + id, '', {headers: this.auth_header});
  }

  public openDevice(id) {
    return this._http.put(this.server_url + '/device/open?device=' + id, '', {headers: this.auth_header});
  }

  public setParams(id, json) {
    const body = {};
    body['params'] = json;
    return this._http.post(this.server_url + '/device/setParams?device=' + id, body, {headers: this.auth_header});
  }

  public closeDevice(id) {
    return this._http.put(this.server_url + '/device/close?device=' + id, '', {headers: this.auth_header});
  }

  public disconnectDevice(id) {
    return this._http.put(this.server_url + '/device/disconnect?device=' + id, '', {headers: this.auth_header});
  }
}
