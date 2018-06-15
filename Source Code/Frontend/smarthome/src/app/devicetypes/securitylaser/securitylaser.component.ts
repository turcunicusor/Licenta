import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-securitylaser',
  templateUrl: './securitylaser.component.html',
  styleUrls: ['./securitylaser.component.css']
})
export class SecuritylaserComponent implements OnInit {
  @Input() deviceId: string;
  @Input() params: {};

  constructor() { }

  ngOnInit() {
  }

}
