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

import {HttpClientModule, HttpClientJsonpModule } from '@angular/common/http';
import {AppSettingsDirective} from './app-settings.directive';

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
  ],
  imports: [
    BrowserModule,
    RouterGlobal,
    AngularFontAwesomeModule,
    HttpClientModule,
    HttpClientJsonpModule
  ],
  providers: [BackendService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
