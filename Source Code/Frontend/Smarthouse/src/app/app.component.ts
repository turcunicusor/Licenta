import {Component, HostListener} from '@angular/core';
import {BackendService} from './backendservice/backend.service';
import {HttpErrorResponse} from '@angular/common/http';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(public _bs: BackendService, private toastr: ToastrService) {
  }

  @HostListener('window:beforeunload', [ '$event' ])
  beforeUnloadHander(event) {
    this._bs.logout().subscribe();
  }
}
