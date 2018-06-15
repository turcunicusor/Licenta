import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-lightbulb',
  templateUrl: './lightbulb.component.html',
  styleUrls: ['./lightbulb.component.css']
})
export class LightbulbComponent implements OnInit {
  @Input() deviceId: string;
  @Input() params: {};

  constructor() { }

  ngOnInit() {
  }

}
