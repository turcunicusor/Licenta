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
  public message;
  public success;
  public error;

  constructor(private router: Router,
              private _bs: BackendService) {
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

  redirect(page: string) {
    this.router.navigate(['/' + page]);
  }

  onLogin(email, password) {
    const body = {email: email, password: password};
    this._bs.login(body)
      .subscribe(
        res => {
          this.success = true;
          this.token = res.headers.get('Authorization').split(' ').pop();
          this.message = 'Login success!';
          this._bs.setToken(this.token);
          setTimeout(() => {
            this.redirect('home');
          }, 1500);
        },
        (err: HttpErrorResponse) => {
          this.error = true;

          console.log('fail');
          console.log(err.error);
          console.log(err.name);
          console.log(err.message);
          console.log(err.status);

          if (err.statusText === 'Unknown Error') {
            this.message = 'Service currently not available.';
          } else {
            this.message = AppSettingsDirective.def_err_message;
          }
        });
    this.error = false;
    this.success = false;
    this.message = '';
    return false;
  }
}
