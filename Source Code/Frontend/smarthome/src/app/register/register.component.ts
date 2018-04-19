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
  public edited;

  constructor(private router: Router, private _backendService: BackendService, private _http: HttpClient) {
    this.edited = false;
  }

  ngOnInit() {
  }

  redirect(page: string) {
    this.router.navigate(['/' + page]);
  }

  onRegister(firstName, lastName, email, password, confirmpassword) {
    this.message = null;
    console.log('register');
    const body = {email: email, firstName: firstName, lastName: lastName, password: password};
    console.log(body);
    this._http.post<any>(AppSettingsDirective.server_url + '/register', JSON.stringify(body),
      {headers: AppSettingsDirective.def_header}).subscribe(
      res => {
        console.log(res);
        this.message = 'succes';
      },
      (err: HttpErrorResponse) => {
        this.message = 'fail';
        console.log('fail');
        console.log(err.error);
        console.log(err.name);
        console.log(err.message);
        console.log(err.status);
      });

    this.edited = true;

    return false;
  }
}
