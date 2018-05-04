import {Component, OnInit} from '@angular/core';
import {BackendService} from '../backendservice/backend.service';
import {HttpErrorResponse} from '@angular/common/http';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(public _bs: BackendService, private toastr: ToastrService) {
  }

  ngOnInit() {
  }

  logout() {
    this._bs.logout().subscribe(
      res => {
        this._bs.logoutSucess();
        this.toastr.success('See you next time!', 'Logout success!');
        this._bs.redirect('/home');
      },
      (err: HttpErrorResponse) => {
        const message = this._bs.handleError(err);
        this.toastr.warning(message);
      });
  }

  redirect(path: string) {
    // if (this._bs.isLoggedIn) {
      this._bs.redirect(path);
    // } else {
    //   this.toastr.warning('You must be logged in to perform this action.', ' ');
    //   setTimeout(() => {
    //     this._bs.redirect('/login');
    //   }, this._bs.timeout);
    // }
  }
}
