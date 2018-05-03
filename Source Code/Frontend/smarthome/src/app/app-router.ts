import {ModuleWithProviders} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {NotfoundComponent} from './notfound/notfound.component';
import {HomeComponent} from './home/home.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {ProfileComponent} from './profile/profile.component';
import {AuthGuardService as AuthGuard} from './backendservice/auth-guard.service';
import {ViewComponent} from './device/view/view.component';
import {AddComponent} from './device/add/add.component';
import {ManageComponent} from './device/manage/manage.component';


export const router: Routes = [
  {path: '', component: HomeComponent},
  {path: 'home', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'profile', component: ProfileComponent}, // canActivate: [AuthGuard]},
  {path: 'devices', component: ViewComponent},
  {path: 'devices/add', component: AddComponent},
  {path: 'devices/manage', component: ManageComponent},
  {path: '**', component: NotfoundComponent},
  // {path: 'notfound', component: NotfoundComponent}
];

// exporter
export const RouterGlobal: ModuleWithProviders = RouterModule.forRoot(router);
