import { Directive } from '@angular/core';
import {HttpHeaders} from '@angular/common/http';

@Directive({
  selector: '[appAppsettings]'
})
export class AppSettingsDirective {
  static def_header = new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*'
    }
  );
  static server_url = 'http://localhost:9000';

  constructor() { }

}
