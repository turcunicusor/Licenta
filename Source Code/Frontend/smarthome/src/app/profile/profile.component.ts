import {Component, OnInit} from '@angular/core';
import {BackendService, User} from '../backendservice/backend.service';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})

export class ProfileComponent implements OnInit {
  public user: User;

  constructor(public _bs: BackendService) {
    _bs.profile().subscribe(
      res => {
        this.user = res;
      },
      (err: HttpErrorResponse) => {
        this._bs.handleError(err);
      });
  }

  ngOnInit() {
  }

}
