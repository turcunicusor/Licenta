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
import {BackendService, Utils} from './backendservice/backend.service';

import {HttpClientModule, HttpClientJsonpModule} from '@angular/common/http';
import {AppSettingsDirective} from './app-settings.directive';

import {ToastrModule} from 'ngx-toastr';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ProfileComponent} from './profile/profile.component';
import {AuthGuardService} from './backendservice/auth-guard.service';
import {AddComponent} from './device/add/add.component';
import {ViewComponent} from './device/view/view.component';
import {ManageComponent} from './device/manage/manage.component';
import {LampComponent} from './devicetypes/lamp/lamp.component';
import { DoorlockComponent } from './devicetypes/doorlock/doorlock.component';
import { SecuritylaserComponent } from './devicetypes/securitylaser/securitylaser.component';
import { LightbulbComponent } from './devicetypes/lightbulb/lightbulb.component';

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
    AddComponent,
    ViewComponent,
    ManageComponent,
    LampComponent,
    DoorlockComponent,
    SecuritylaserComponent,
    LightbulbComponent,
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
  providers: [BackendService, AuthGuardService, Utils],
  bootstrap: [AppComponent]
})
export class AppModule {
}
