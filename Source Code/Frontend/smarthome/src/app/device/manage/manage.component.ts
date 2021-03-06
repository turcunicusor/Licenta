import {AfterViewInit, Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {ToastrService} from 'ngx-toastr';
import {BackendService, Device, Utils} from '../../backendservice/backend.service';
import {ActivatedRoute} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';
import {LampComponent} from '../../devicetypes/lamp/lamp.component';

@Component({
  selector: 'app-manage',
  templateUrl: './manage.component.html',
  styleUrls: ['./manage.component.css'],
})
export class ManageComponent implements OnInit, OnDestroy {
  id: string;
  private sub: any;
  device: Device;
  public isLoading;
  public connectionStatus: boolean;
  private cachedIp;
  private cachedPort;
  private cachedType;

  constructor(private route: ActivatedRoute, public _bs: BackendService, private toastr: ToastrService, public _ut: Utils) {
    this.isLoading = true;
    this.connectionStatus = null;
  }

  updateInputValues(ip, port, type) {
    (<HTMLInputElement>document.getElementById('inputip')).value = ip;
    (<HTMLInputElement>document.getElementById('inputport')).value = port;
  }

  updateDangerZone(status) {
    if (status) {
      (<HTMLInputElement>document.getElementById('dangerZoneFieldSet')).setAttribute('disabled', 'disabled');
      (<HTMLInputElement>document.getElementById('buttonDeleteDevice')).setAttribute('disabled', 'disabled');
    } else {
      (<HTMLInputElement>document.getElementById('dangerZoneFieldSet')).removeAttribute('disabled');
      (<HTMLInputElement>document.getElementById('buttonDeleteDevice')).removeAttribute('disabled');
    }
  }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      this.id = params['id'];
      this.device = new Device();
      this._bs.getDevice(this.id).subscribe(
        res => {
          this.device = res;
          this.isLoading = false;
          this.cachedIp = this.device.ip;
          this.cachedPort = this.device.port;
          this.cachedType = this.device.type;
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

  onSaveDangerZone(ip: string, port: string, type: string) {
    const cachedIp = this.device.ip;
    const cachedPort = this.device.port;
    const cachedType = this.device.type;
    const json = {ip: ip, port: port, type: type, name: ''};
    this._bs.updateDevice(json, this.device.id).subscribe(
      res => {
        this.toastr.success('Device updated successfully.');
        this.device.status = 'disconnected';
        this.device.params = {};
        this.device.acceptedParams = {};
        this.cachedIp = this.device.ip;
        this.cachedPort = this.device.port;
        this.cachedType = this.device.type;
      },
      (err: HttpErrorResponse) => {
        this.device.ip = this.cachedIp;
        this.device.port = this.cachedPort;
        this.device.type = this.cachedType;
        this.device.status = 'disconnected';
        this.updateInputValues(this.device.ip, this.device.port, this.device.type);
        const message = this._bs.handleError(err);
        this.toastr.warning(message);
        this.device.params = {};
      });
    return false;
  }

  onSaveParam(key: string, valueindex: string) {
    const value = ((document.getElementById('param' + valueindex) as HTMLInputElement).value);
    const params = {};
    params[key] = value;
    this._bs.setParams(this.device.id, params).subscribe(
      res => {
        this.toastr.success('Parameter \'' + key + '\' updated successfully.');
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
    return this._bs.connectDevice(this.id).subscribe(
      res => {
        this._bs.getDevice(this.id).subscribe(
          rest => {
            this.device.params = rest.params;
            this.device.acceptedParams = rest.acceptedParams;
            this.device.status = rest.status;
            this.device.isOpened = rest.isOpened;
            this.updateDangerZone(true);
          },
          (err: HttpErrorResponse) => {
            const message = this._bs.handleError(err);
            this.toastr.warning(message);
          });
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
        this.device.params = {};
        this.device.acceptedParams = {};
        this.device.isOpened = false;
        this.updateDangerZone(false);
      },
      (err: HttpErrorResponse) => {
        const message = this._bs.handleError(err);
        this.toastr.warning(message);
      });
  }

  onChange(isChecked: boolean) {
    if (isChecked) {
      this._bs.openDevice(this.device.id).subscribe(
        res => {
          this.device.status = 'opened';
        },
        (err: HttpErrorResponse) => {
          const message = this._bs.handleError(err);
          this.toastr.warning(message);
        });
    } else {
      this._bs.closeDevice(this.device.id).subscribe(
        res => {
          this.device.status = 'closed';
        },
        (err: HttpErrorResponse) => {
          const message = this._bs.handleError(err);
          this.toastr.warning(message);
        });
    }
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }
}
