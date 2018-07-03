import {Component, Input, OnDestroy, OnInit, ViewEncapsulation} from '@angular/core';
import {TimerObservable} from 'rxjs/observable/TimerObservable';
import {HttpErrorResponse} from '@angular/common/http';
import {BackendService, Device} from '../../backendservice/backend.service';
import {ToastrService} from 'ngx-toastr';
import {Observable} from 'rxjs/Observable';
import {timer} from 'rxjs/observable/timer';

@Component({
  selector: 'app-homeenvironment',
  encapsulation: ViewEncapsulation.None,
  templateUrl: './homeenvironment.component.html',
  styleUrls: ['./homeenvironment.component.css']
})
export class HomeenvironmentComponent implements OnInit, OnDestroy {
  public subscription: any;
  @Input() deviceId: string;
  @Input() params: {};
  private device: Device;
  temperature: any = 3;
  humidity: any = 100;
  timerClock: any;

  constructor(public _bs: BackendService, private toastr: ToastrService) {
  }

  ngOnInit() {
    this.timerClock = timer(2000, 1000);
    this.subscription = this.timerClock.subscribe(t => {
      this.getUpdate();
    });
    this.temperature = +this.params['temperature'];
    this.humidity = +this.params['humidity'];
  }

  getUpdate() {
    this._bs.getDevice(this.deviceId).subscribe(
      res => {
        this.device = res;
        this.temperature = +this.device.params['temperature'];
        this.humidity = +this.device.params['humidity'];
      },
      (err: HttpErrorResponse) => {
        this.subscription.unsubscribe();
        // const message = this._bs.handleError(err);
        this.toastr.warning('Could not receive updates from device.');
      });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
