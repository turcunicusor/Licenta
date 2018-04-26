import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {HeaderComponent} from './header/header.component';
import {FooterComponent} from './footer/footer.component';
import {NotfoundComponent} from './notfound/notfound.component';
import {RouterGlobal} from './app-router';
import {HomeComponent} from './home/home.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {CloseMenuDirective} from './close-menu.directive';
import {AngularFontAwesomeModule} from 'angular-font-awesome';
import {BackendService} from './backendservice/backend.service';

import {HttpClientModule, HttpClientJsonpModule} from '@angular/common/http';
import {AppSettingsDirective} from './app-settings.directive';

import {ToastrModule} from 'ngx-toastr';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { ProfileComponent } from './profile/profile.component';
import {AuthGuardService} from './backendservice/auth-guard.service';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    NotfoundComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    CloseMenuDirective,
    AppSettingsDirective,
    ProfileComponent,
  ],
  imports: [
    BrowserModule,
    RouterGlobal,
    AngularFontAwesomeModule,
    HttpClientModule,
    HttpClientJsonpModule,
    ToastrModule.forRoot({
      timeOut: 5000,
      positionClass: 'toast-bottom-center',
      preventDuplicates: true,
    }),
    BrowserAnimationsModule
  ],
  providers: [BackendService, AuthGuardService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
