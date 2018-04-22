import {Component, OnInit} from '@angular/core';
import {BackendService} from '../backendservice/backend.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(public _bs: BackendService) {
  }

  ngOnInit() {
  }
}
