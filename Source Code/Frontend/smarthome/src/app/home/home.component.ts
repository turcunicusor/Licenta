import {Component, OnInit} from '@angular/core';
import {BackendService} from '../backendservice/backend.service';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  providers: []
})


export class HomeComponent implements OnInit {
  public dataGet;

  constructor(public _bs: BackendService) {
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
        this._bs.logError(err);

        // if (err.statusText === 'Unknown Error') {
        //   this.message = 'Service currently not available.';
        // } else {
        //   this.message = AppSettingsDirective.def_err_message;
        // }
      });
  }
}
