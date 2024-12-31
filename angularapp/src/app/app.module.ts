import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AdmineditloanComponent } from './components/admineditloan/admineditloan.component';
import { AdminnavComponent } from './components/adminnav/adminnav.component';
import { AdminviewfeedbackComponent } from './components/adminviewfeedback/adminviewfeedback.component';
import { CreateloanComponent } from './components/createloan/createloan.component';
import { ErrorComponent } from './components/error/error.component';
import { HomeComponent } from './components/home/home.component';
import { LoanformComponent } from './components/loanform/loanform.component';
import { LoginComponent } from './components/login/login.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { RequestedloanComponent } from './components/requestedloan/requestedloan.component';
import { UsernavComponent } from './components/usernav/usernav.component';
import { UserviewloanComponent } from './components/userviewloan/userviewloan.component';
import { ViewloanComponent } from './components/viewloan/viewloan.component';
import { UserappliedloanComponent } from './components/userappliedloan/userappliedloan.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AuthInterceptor } from './interceptor/auth.interceptor';
import { SearchPipe } from './pipes/search.pipe';
import { FilterPipe } from './pipes/filter.pipe';
import { UseraddfeedbackComponent } from './components/useraddfeedback/useraddfeedback.component';
import { UserviewfeedbackComponent } from './components/userviewfeedback/userviewfeedback.component';
import { SearchLoanPipe } from './pipes/search-loan.pipe';
import { AuthService } from './services/auth.service';
import { AdminGuard } from './components/authguard/adminguard.component';
import { UserGuard } from './components/authguard/userguard.component';
import { AuthGuard } from './components/authguard/authguard.component';

@NgModule({
  declarations: [
    AppComponent,
    AdmineditloanComponent,
    AdminnavComponent,
    AdminviewfeedbackComponent,
    CreateloanComponent,
    ErrorComponent,
    HomeComponent,
    LoanformComponent,
    LoginComponent,
    NavbarComponent,
    RegistrationComponent,
    RequestedloanComponent,
    UsernavComponent,
    UserviewloanComponent,
    ViewloanComponent,
    UserappliedloanComponent,
    SearchPipe,
    FilterPipe,
    UseraddfeedbackComponent,
    UserviewfeedbackComponent,
    SearchLoanPipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule,
    
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true
    },
    AuthService,
    AuthGuard,
    AdminGuard,
    UserGuard
    
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
