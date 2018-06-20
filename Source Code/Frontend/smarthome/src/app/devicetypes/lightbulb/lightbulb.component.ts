import {Component, Input, OnInit} from '@angular/core';
import {ToastrService} from 'ngx-toastr';
import {BackendService} from '../../backendservice/backend.service';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-lightbulb',
  templateUrl: './lightbulb.component.html',
  styleUrls: ['./lightbulb.component.css']
})
export class LightbulbComponent implements OnInit {
  @Input() deviceId: string;
  @Input() params: {};

  status: any;

  constructor(public _bs: BackendService, private toastr: ToastrService) {
  }

  ngOnInit() {
    if (this.params['red'] === 'true') {
      this.status = 'red';
    } else if (this.params['green'] === 'true') {
      this.status = 'green';
    } else {
      this.status = 'gray';
    }
    console.log(this.params);
  }

  onLightBulbClick(color) {
    const params = {'red': 'false', 'green': 'false'};
    if (color === 'red') {
      params['red'] = 'true';
    } else if (color === 'green') {
      params['green'] = 'true';
    }
    // console.log(params);
    this._bs.setParams(this.deviceId, params).subscribe(
      res => {
        // if (state === true) {
        //   this.toastr.success('State \'' + event.point.name + '\' updated successfully.');
        // }
        this.status = color;
      },
      (err: HttpErrorResponse) => {
        const message = this._bs.handleError(err);
        this.toastr.warning(message);
      });
  }
}
