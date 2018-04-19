import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {BackendService} from '../backendservice/backend.service';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {AppSettingsDirective} from '../app-settings.directive';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  public token;

  constructor(private router: Router,
              private _backendService: BackendService,
              private _http: HttpClient) {
    this.token = 'no token';
  }

  ngOnInit() {
    // reset login status
    // this.authenticationService.logout();

    // get return url from route parameters or default to '/'
    // this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  redirect(page: string) {
    this.router.navigate(['/' + page]);
  }

  onLogin(email, password) {
    const body = {email: email, password: password};
    console.log(body);
    this._http.post<any>(AppSettingsDirective.server_url + '/login', JSON.stringify(body),
      {headers: AppSettingsDirective.def_header}).subscribe(
      res => {
        this.token = res.headers.get('AUTHORIZATION');
      },
      (err: HttpErrorResponse) => {
        console.log('fail');
        console.log(err.error);
        console.log(err.name);
        console.log(err.message);
        console.log(err.status);
      });
    return false;
  }
}
