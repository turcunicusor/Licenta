import {Component, OnInit} from '@angular/core';
import {BackendService, Device, Utils} from '../../backendservice/backend.service';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent implements OnInit {
  constructor(public _bs: BackendService, private toastr: ToastrService, public _ut: Utils) {
  }

  ngOnInit() {
  }

}
