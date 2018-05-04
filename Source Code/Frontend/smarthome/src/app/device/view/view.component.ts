import {Component, OnInit} from '@angular/core';
import {Device} from '../../backendservice/backend.service';

@Component({
  selector: 'app-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.css']
})
export class ViewComponent implements OnInit {
  devices: Array<Device>;
  firstDevice;
  device: Device;

  constructor() {
    // test only
    this.device = new Device();
    this.devices = new Array<Device>();
    for (let i = 0; i < 20; i++) {
      this.devices.push(new Device());
      this.devices[i].id = this.devices[i].id + i;
    }
    this.firstDevice = this.devices[0];
    this.devices.shift();
  }

  ngOnInit() {
  }

}
