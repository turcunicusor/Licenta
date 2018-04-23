import {Component, OnInit} from '@angular/core';
import {BackendService} from '../backendservice/backend.service';
import {HttpErrorResponse} from '@angular/common/http';
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

  constructor(public _bs: BackendService) {
    this.success = false;
    this.error = false;
  }

  ngOnInit() {
  }

  onRegister(firstName, lastName, email, password, confirmpassword) {
    this.message = null;
    if (password !== confirmpassword) {
      this.message = 'Password does not match the confirm password.';
      this.error = true;
    } else {
      const body = {email: email, firstName: firstName, lastName: lastName, password: password};
      this.success = false;
      this.error = false;
      this._bs.register(body)
        .subscribe(
          res => {
            this.success = true;
            this.message = 'Register success! Redirecting to login...';
            setTimeout(() => {
              this._bs.redirect('login');
            }, 1500);
          },
          (err: HttpErrorResponse) => {
            this.error = true;
            this.message = this._bs.handleError(err);
          });
    }
    return false;
  }
}
