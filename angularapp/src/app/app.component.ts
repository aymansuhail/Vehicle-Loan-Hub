import { Component, OnInit } from '@angular/core';
import { AuthService } from './services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Vehicle-Loan-Hub';
  // isLoggedIn: boolean = false;
  // userRole: string | null = null

  // constructor(private authService: AuthService, private router: Router) { }

  // ngOnInit() {
  // Check if user is already logged in
  // if (this.authService.isLoggedIn()) {
  //   this.userRole = this.authService.getUserRole();
  //   this.isAdminUser = this.isAdmin();
  //   this.isNormalUser = this.isUser();
  //   if (this.userRole === 'ADMIN') {
  //     this.router.navigate(['/admin']);
  //   } else if (this.userRole === 'USER') {
  //     this.router.navigate(['/user']);
  //   }
  // } else {
  //   this.userRole = null;
  //   this.isAdminUser = false;
  //   this.isNormalUser = false;
  //   // Navigate to home if user is not logged in
  //   this.router.navigate(['/home']);
  // }

  // }

  // isAdmin(): boolean {
  //   return this.userRole === 'ADMIN';
  // }

  // isUser(): boolean {
  //   return this.userRole === 'USER';
  // private authService: AuthService, private router: Router
  // }
  constructor() { } 
  ngOnInit() {
    // this.isLoggedIn = this.authService.isLoggedIn();
    //  if (this.isLoggedIn) {
    //   const currentUser = JSON.parse(localStorage.getItem('currentUser') || '{}');
    //   this.userRole = currentUser.userRole; if (this.userRole === 'ADMIN') {
    //     this.router.navigate(['/admin']);
    //   }
    //   else if (this.userRole === 'USER') { this.router.navigate(['/user']); }
    // } else {
    //   this.router.navigate(['/home']);
    // }
  }
}
