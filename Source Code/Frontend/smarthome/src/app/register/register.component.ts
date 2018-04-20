import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {BackendService} from '../backendservice/backend.service';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {AppSettingsDirective} from '../app-settings.directive';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  public message;
  public success;
  public error;

  constructor(private router: Router, private _bs: BackendService) {
    this.success = false;
    this.error = false;
  }

  ngOnInit() {
  }

  redirect(page: string) {
    this.router.navigate(['/' + page]);
  }

  onRegister(firstName, lastName, email, password, confirmpassword) {
    this.message = null;
    const body = {email: email, firstName: firstName, lastName: lastName, password: password};
    this.success = false;
    this._bs.register(body)
      .subscribe(
        res => {
          this.success = true;
          this.message = 'Register success! Redirecting to login...';
          setTimeout(() => {
            this.redirect('login');
          }, 1500);
        },
        (err: HttpErrorResponse) => {
          this.error = true;
          if (err.statusText === 'Unknown Error') {
            this.message = 'Service currently not available.';
          } else if (err.status !== 500) {
            this.message = err.error;
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
