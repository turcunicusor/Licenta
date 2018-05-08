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
  public connectionStatus: boolean;

  constructor(private route: ActivatedRoute, public _bs: BackendService, private toastr: ToastrService, public _ut: Utils) {
    this.isLoading = true;
    this.connectionStatus = null;
  }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      this.id = params['id'];
      this.device = new Device();
      this._bs.getDevice(this.id).subscribe(
        res => {
          this.device = res;
          this.isLoading = false;
          console.log(this.device.acceptedParams);
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

  onConnect() {
    return this._bs.connectDevice(this.device.id).subscribe(
      res => {
        this.device.status = 'connected';
      },
      (err: HttpErrorResponse) => {
        const message = this._bs.handleError(err);
        this.toastr.warning(message);
      });
  }

  onDisconnect() {
    return this._bs.disconnectDevice(this.device.id).subscribe(
      res => {
        this.device.status = 'disconnected';
      },
      (err: HttpErrorResponse) => {
        const message = this._bs.handleError(err);
        this.toastr.warning(message);
      });
  }

  // onTestConnection(id: string) {
  //   this._bs.testConnectionDevice(id).subscribe(res => {
  //       this.connectionStatus = true;
  //     },
  //     (err: HttpErrorResponse) => {
  //       this.connectionStatus = false;
  //       const message = this._bs.handleError(err);
  //       this.toastr.warning(message);
  //     });
  // }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }
}
