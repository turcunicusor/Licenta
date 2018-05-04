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

  redirect(path: string) {
    if (this._bs.isLoggedIn) {
      this._bs.redirect(path);
    } else {
      this.toastr.warning('You must be logged in to perform this action.', ' ');
      setTimeout(() => {
        this._bs.redirect('/login');
      }, this._bs.timeout);
    }
  }
}
