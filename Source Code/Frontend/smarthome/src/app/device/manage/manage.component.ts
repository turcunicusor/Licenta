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
  public isLoading;

  constructor(private route: ActivatedRoute, public _bs: BackendService, private toastr: ToastrService, public _ut: Utils) {
    this.isLoading = true;
  }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      this.id = params['id'];
      this.device = new Device();
      this._bs.getDevice(this.id).subscribe(
        res => {
          this.device = res;
          this.isLoading = false;
        },
        (err: HttpErrorResponse) => {
          const message = this._bs.handleError(err);
          this.toastr.warning(message);
        });
    });
  }

  onSaveName(name: string) {
    const json = {ip: '', port: '', type: '', name: name};
    this._bs.updateDevice(json, this.device.id).subscribe(
      res => {
        this.toastr.success('Device name updated successfully.');
      },
      (err: HttpErrorResponse) => {
        const message = this._bs.handleError(err);
        this.toastr.warning(message);
      });
    return false;
  }

  onSaveParam(key: string, valueindex: string) {
    const value = ((document.getElementById('param' + valueindex) as HTMLInputElement).value);
    const val = key.concat(value);
    alert(val);
    return false;
  }

  onSaveDangerZone(ip: string, port: string, type: string) {
    const json = {ip: ip, port: port, type: type, name: ''};
    this._bs.updateDevice(json, this.device.id).subscribe(
      res => {
        this.toastr.success('Device updated successfully.');
      },
      (err: HttpErrorResponse) => {
        const message = this._bs.handleError(err);
        this.toastr.warning(message);
      });
    return false;
  }

  onDeleteDevice() {
    this._bs.deleteDevice(this.device.id).subscribe(
      res => {
        this.toastr.success('Device \''.concat(this.device.name).concat('\' deleted successfully.'));
        this._bs.redirect('/devices');
      },
      (err: HttpErrorResponse) => {
        const message = this._bs.handleError(err);
        this.toastr.warning(message);
      });
    return false;
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }
}
