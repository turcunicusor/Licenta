import {Component, OnInit} from '@angular/core';
import {BackendService} from '../backendservice/backend.service';
import {HttpErrorResponse} from '@angular/common/http';
import {AppSettingsDirective} from '../app-settings.directive';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  public token;
  public message;
  public success;
  public error;

  constructor(public _bs: BackendService) {
    this.token = 'no token';
    this.success = false;
    this.error = false;
  }

  ngOnInit() {
    // reset login status
    // this.authenticationService.logout();

    // get return url from route parameters or default to '/'
    // this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  onLogin(email, password) {
    const body = {email: email, password: password};
    this._bs.login(body)
      .subscribe(
        res => {
          this.success = true;
          this.token = res.headers.get('Authorization').split(' ').pop();
          this.message = 'Login success!';
          setTimeout(() => {
            this._bs.setToken(this.token);
            this._bs.redirect('home');
          }, this._bs.timeout);
        },
        (err: HttpErrorResponse) => {
          this.error = true;
          this.message = this._bs.handleError(err);
        });
    this.error = false;
    this.success = false;
    this.message = '';
    return false;
  }
}
