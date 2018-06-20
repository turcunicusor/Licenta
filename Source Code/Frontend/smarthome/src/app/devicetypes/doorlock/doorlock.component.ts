import {Component, Input, OnInit} from '@angular/core';
import {HttpErrorResponse} from '@angular/common/http';
import {BackendService} from '../../backendservice/backend.service';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-doorlock',
  templateUrl: './doorlock.component.html',
  styleUrls: ['./doorlock.component.css']
})
export class DoorlockComponent implements OnInit {
  @Input() deviceId: string;
  @Input() params: {};

  status: boolean;

  constructor(public _bs: BackendService, private toastr: ToastrService) {
  }

  ngOnInit() {
    this.status = this.params['lock'] === 'true';
  }

  onLockClick(status) {
    const params = {'lock': status};
    this._bs.setParams(this.deviceId, params).subscribe(
      res => {
        // if (state === true) {
        //   this.toastr.success('State \'' + event.point.name + '\' updated successfully.');
        // }
        this.status = !this.status;
      },
      (err: HttpErrorResponse) => {
        const message = this._bs.handleError(err);
        this.toastr.warning(message);
      });
  }

}
