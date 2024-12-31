import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { LoginComponent } from './components/login/login.component';
import { ErrorComponent } from './components/error/error.component';
import { ViewloanComponent } from './components/viewloan/viewloan.component';
import { CreateloanComponent } from './components/createloan/createloan.component';
import { AdminviewfeedbackComponent } from './components/adminviewfeedback/adminviewfeedback.component';
import { AdmineditloanComponent } from './components/admineditloan/admineditloan.component';
import { RequestedloanComponent } from './components/requestedloan/requestedloan.component';
import { UserviewloanComponent } from './components/userviewloan/userviewloan.component';
import { LoanformComponent } from './components/loanform/loanform.component';
import { UserappliedloanComponent } from './components/userappliedloan/userappliedloan.component';
import { UserviewfeedbackComponent } from './components/userviewfeedback/userviewfeedback.component';
import { UseraddfeedbackComponent } from './components/useraddfeedback/useraddfeedback.component';
import { AdminGuard } from './components/authguard/adminguard.component';
import { UserGuard } from './components/authguard/userguard.component';
import { AuthGuard } from './components/authguard/authguard.component';


const routes: Routes = [
  { path: "home", component: HomeComponent, canActivate : [AuthGuard] },
  { path: "", redirectTo: "/home", pathMatch: "full" },
  { path: "admin", component: HomeComponent, canActivate: [AdminGuard]},
  { path: "admin/viewloans", component: ViewloanComponent, canActivate: [AdminGuard]},
  { path: "admin/createloans", component: CreateloanComponent, canActivate: [AdminGuard]},
  { path: "admin/viewfeedbacks", component: AdminviewfeedbackComponent, canActivate: [AdminGuard] },
  { path: "admin/editloan/:id", component: AdmineditloanComponent, canActivate: [AdminGuard]},
  { path: "admin/requestedloan", component: RequestedloanComponent, canActivate: [AdminGuard] },
  { path: "user", component: HomeComponent, canActivate: [UserGuard] },
  { path: "user/viewloans", component: UserviewloanComponent, canActivate: [UserGuard]},
  { path: "user/loanapplication/:loanId", component: LoanformComponent, canActivate: [UserGuard] },
  { path: "user/userappliedloan", component: UserappliedloanComponent, canActivate: [UserGuard]},
  { path: "user/viewfeedbacks", component: UserviewfeedbackComponent, canActivate: [UserGuard]},
  { path: "user/addfeedbacks", component: UseraddfeedbackComponent, canActivate: [UserGuard]},
  { path: "register", component: RegistrationComponent },
  { path: "login", component: LoginComponent },
  {path : "error" , component : ErrorComponent},
  { path: "**", component: ErrorComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
