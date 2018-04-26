import {Component, OnInit} from '@angular/core';
import {BackendService} from '../backendservice/backend.service';

@Component({
  selector: 'app-notfound',
  templateUrl: './notfound.component.html',
  styleUrls: ['./notfound.component.css']
})
export class NotfoundComponent implements OnInit {

  constructor(public _bs: BackendService) {
  }

  ngOnInit() {
  }
}
