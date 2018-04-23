import {Component, OnInit} from '@angular/core';
import {BackendService} from '../backendservice/backend.service';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(public _bs: BackendService) {
  }

  ngOnInit() {
  }

  logout() {
    this._bs.logout().subscribe(
      res => {
        this._bs.logoutSucess();
      },
      (err: HttpErrorResponse) => {
        this._bs.handleError(err);
      });
  }
}
