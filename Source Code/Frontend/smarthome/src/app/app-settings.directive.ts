import {Directive} from '@angular/core';
import {HttpErrorResponse, HttpHeaders} from '@angular/common/http';

@Directive({
  selector: '[appAppsettings]'
})

export class AppSettingsDirective {

  constructor() {
  }
  static def_err_message = 'Ooops! Something whent wrong..';
}
