import {Directive} from '@angular/core';
import {HttpErrorResponse, HttpHeaders} from '@angular/common/http';

@Directive({
  selector: '[appAppsettings]'
})

export class AppSettingsDirective {

  constructor() {
  }

  static server_url = 'http://75eb8d39.ngrok.io';
  static def_err_message = 'Ooops! Something whent wrong..';

  static delay(ms: number) {
    return new Promise(resolve => setTimeout(resolve, ms));
  }
}
