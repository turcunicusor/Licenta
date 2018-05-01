import {Component, OnInit} from '@angular/core';
import {BackendService} from '../backendservice/backend.service';
import {HttpErrorResponse} from '@angular/common/http';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  providers: []
})


export class HomeComponent implements OnInit {
  public dataGet;

  constructor(public _bs: BackendService,  private toastr: ToastrService) {
  }

  ngOnInit() {
  }

  getData() {
    this._bs.getAllUsers().subscribe(
      res => {
        this.dataGet = res;
        console.log(res);
      },
      (err: HttpErrorResponse) => {
        // this.error = true;
        this._bs.handleError(err);

        // if (err.statusText === 'Unknown Error') {
        //   this.message = 'Service currently not available.';
        // } else {
        //   this.message = AppSettingsDirective.def_err_message;
        // }
      });
  }

  redirect(path: string) {
    if (this._bs.isLoggedIn) {
      this._bs.redirect(path);
    } else {
      this.toastr.warning('You must be logged in to perform this action.', ' ');
      this._bs.redirect('/login');
    }
  }
}
