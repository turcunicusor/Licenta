import {Component, OnDestroy, OnInit} from '@angular/core';
import {ToastrService} from 'ngx-toastr';
import {BackendService, Device, Utils} from '../../backendservice/backend.service';
import {ActivatedRoute} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-manage',
  templateUrl: './manage.component.html',
  styleUrls: ['./manage.component.css']
})
export class ManageComponent implements OnInit, OnDestroy {
  id: string;
  private sub: any;
  device: Device;

  constructor(private route: ActivatedRoute, public _bs: BackendService, private toastr: ToastrService, public _ut: Utils) {
  }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      this.id = params['id'];
      this.device = new Device();
      this._bs.getDevice(this.id).subscribe(
        res => {
          this.device = res;
        },
        (err: HttpErrorResponse) => {
          const message = this._bs.handleError(err);
          this.toastr.warning(message);
        });
    });
  }

  onSaveName(name: string) {
  }

  onSaveParam(key: string, valueindex: string) {
    const value = ((document.getElementById('param' + valueindex) as HTMLInputElement).value);
    const val = key.concat(value);
    alert(val);
  }

  onSaveDangerZone(ip: string, port: string, type: string, name: string) {
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }
}
