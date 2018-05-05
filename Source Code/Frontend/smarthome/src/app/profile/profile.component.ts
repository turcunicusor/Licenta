import {Component, OnInit} from '@angular/core';
import {BackendService, User} from '../backendservice/backend.service';
import {HttpErrorResponse} from '@angular/common/http';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})

export class ProfileComponent implements OnInit {
  public user: User;
  private userSave: User;
  public isLoading: boolean;

  constructor(public _bs: BackendService, private toastr: ToastrService) {
    this.isLoading = true;
  }


  ngOnInit() {
    this.user = new User();
    this._bs.profile().subscribe(
      res => {
        this.user = res;
        this.userSave = this.user;
        this.isLoading = false;
      },
      (err: HttpErrorResponse) => {
        this._bs.handleError(err);
      });
  }

  onSaveChanges(firstName, lastName, email, oldpassword, newpassword, confirmnewpassword) {
    if (newpassword !== '' || confirmnewpassword !== '') {
      if (oldpassword === '') {
        this.toastr.warning('You must fill old password filed.', ' ');
        return false;
      }
      if (newpassword !== confirmnewpassword) {
        this.toastr.warning('Password does not match the confirm password.', ' ');
        return false;
      }
    }
    const user = {
      firstName: firstName, lastName: lastName,
      oldEmail: this.user.email, newEmail: email,
      oldPassword: oldpassword, newPassword: newpassword
    };
    this._bs.updateProfile(user).subscribe(
      res => {
        this.toastr.success('Profile information updated successfully.');
        this._bs.updateEmail(email);
        this._bs.redirect('/profile');
      },
      (err: HttpErrorResponse) => {
        const message = this._bs.handleError(err);
        this.toastr.warning(message);
      });
    return false;
  }
}
