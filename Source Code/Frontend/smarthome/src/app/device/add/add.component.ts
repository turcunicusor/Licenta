import {Component, OnInit} from '@angular/core';
import {BackendService, Device, Utils} from '../../backendservice/backend.service';
import {ToastrService} from 'ngx-toastr';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent implements OnInit {

  constructor(public _bs: BackendService, private toastr: ToastrService, public _ut: Utils) {
  }

  ngOnInit() {
  }

  onSaveChanges(ip: string, port: number, type: string, name: string) {
    const json = {ip: ip, port: port, type: type, name: name};
    this._bs.addNewDevice(json).subscribe(
      res => {
        this.toastr.success('You can visualize your devices on view page.', 'Device added successfully.');
      },
      (err: HttpErrorResponse) => {
        const message = this._bs.handleError(err);
        this.toastr.warning(message);
      });
    return false;
  }
}
