import {Component, OnInit} from '@angular/core';
import {BackendService, Device, Utils} from '../../backendservice/backend.service';
import {ToastrService} from 'ngx-toastr';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.css']
})
export class ViewComponent implements OnInit {
  devices: Array<Device>;
  public isLoading: boolean;

  constructor(public _bs: BackendService, private toastr: ToastrService, public _ut: Utils) {
    // test only
    // this.devices = [];
    // for (let i = 0; i < 20; i++) {
    //   this.devices.push(new Device());
    //   this.devices[i].id = this.devices[i].id + i;
    // }
    this.isLoading = true;
  }

  onManageClick(id: string) {
    this._bs.redirectWithParam('/devices/manage', id);
  }

  ngOnInit() {
    this.devices = [];
    this._bs.getAllDevices().subscribe(
      res => {
        this.devices = res;
        this.isLoading = false;
      },
      (err: HttpErrorResponse) => {
        const message = this._bs.handleError(err);
        this.toastr.warning(message);
      });
  }
}
