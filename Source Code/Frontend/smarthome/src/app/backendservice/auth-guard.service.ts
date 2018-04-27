import {Injectable} from '@angular/core';
import {CanActivate} from '@angular/router';
import {BackendService} from './backend.service';

@Injectable()
export class AuthGuardService implements CanActivate {

  constructor(public _bs: BackendService) {
  }

  canActivate(): boolean {
    if (!this._bs.isLoggedIn) {
      this._bs.redirect('/home');
      return false;
    }
    return true;
  }
}
