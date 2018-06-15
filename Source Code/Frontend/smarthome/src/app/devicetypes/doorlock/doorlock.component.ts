import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-doorlock',
  templateUrl: './doorlock.component.html',
  styleUrls: ['./doorlock.component.css']
})
export class DoorlockComponent implements OnInit {
  @Input() deviceId: string;
  @Input() params: {};

  constructor() { }

  ngOnInit() {
  }

}
