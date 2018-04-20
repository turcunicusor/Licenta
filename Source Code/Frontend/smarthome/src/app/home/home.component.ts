import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {BackendService} from '../backendservice/backend.service';
import {AppSettingsDirective} from '../app-settings.directive';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  providers: []
})


export class HomeComponent implements OnInit {
  public dataGet;

  constructor(private router: Router, private _bs: BackendService) {
  }

  ngOnInit() {
  }

  redirect(page: string) {
    this.router.navigate(['/' + page]);
  }

  getData() {
    this._bs.getAllUsers().subscribe(
      res => {
        this.dataGet = res;
      },
      (err: HttpErrorResponse) => {
        // this.error = true;

        console.log('fail');
        console.log(err.error);
        console.log(err.name);
        console.log(err.message);
        console.log(err.status);

        // if (err.statusText === 'Unknown Error') {
        //   this.message = 'Service currently not available.';
        // } else {
        //   this.message = AppSettingsDirective.def_err_message;
        // }
      });
  }
}
