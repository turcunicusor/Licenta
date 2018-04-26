import {ModuleWithProviders} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {NotfoundComponent} from './notfound/notfound.component';
import {HomeComponent} from './home/home.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {ProfileComponent} from './profile/profile.component';
import {AuthGuardService as AuthGuard} from './backendservice/auth-guard.service';


// export const router : RouterGlobal = [
//   {path:'', component: DashboardComponent, children: [
//       {path:'', redirectTo: 'home', pathMatch:'full'},
//       {path: 'profile', component: ProfileComponent},
//       {path:'home', component: HomeComponent},
//       {path:'play', component: PlayComponent},
//       {path:'live', component: LiveComponent},
//       {path:'clubs', component: ClubsComponent},
//       {path:'learn', component: LearnComponent},
//       {path:'forum', component: ForumComponent},
//       {path:'status', component: StatsComponent},
//       {path:'help', component: HelpComponent}
//     ]},
//   {path: 'login', component: LoginComponent},
//   {path: 'register', component: RegisterComponent},
//   {path: '**', redirectTo: '/404', pathMatch: 'full'}
// ];

export const router: Routes = [
  {path: '', component: HomeComponent},
  {path: 'home', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'profile', component: ProfileComponent}, // canActivate: [AuthGuard]},
  {path: '**', component: NotfoundComponent},
  // {path: 'notfound', component: NotfoundComponent}
];

// exporter
export const RouterGlobal: ModuleWithProviders = RouterModule.forRoot(router);
