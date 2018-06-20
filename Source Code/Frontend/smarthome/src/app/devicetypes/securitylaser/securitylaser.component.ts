import {Component, Input, OnInit} from '@angular/core';
import {HttpErrorResponse} from '@angular/common/http';
import {ToastrService} from 'ngx-toastr';
import {BackendService} from '../../backendservice/backend.service';

@Component({
  selector: 'app-securitylaser',
  templateUrl: './securitylaser.component.html',
  styleUrls: ['./securitylaser.component.css']
})
export class SecuritylaserComponent implements OnInit {
  @Input() deviceId: string;
  @Input() params: {};

  status: boolean;

  constructor(public _bs: BackendService, private toastr: ToastrService) {
  }
  ngOnInit() {
    this.status = this.params['laser'] === 'true';
  }

  onLaserClick(status) {
    const params = {'laser': status};
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
