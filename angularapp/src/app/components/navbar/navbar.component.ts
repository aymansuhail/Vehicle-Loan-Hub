
import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  isLoggedIn: boolean = false;
  userRole: string | null = null;
  username: string | null = null;
  showLogoutPopup: boolean = false;

  constructor(private authService: AuthService, private router: Router, private cdr: ChangeDetectorRef) { }

  ngOnInit() {
    this.isLoggedIn = this.authService.isLoggedIn();
    if (this.isLoggedIn) {
      const currentUser = JSON.parse(localStorage.getItem('currentUser') || '{}');
      this.userRole = currentUser.userRole;
      this.username = currentUser.username;
    }

    this.authService.userRoleChanged.subscribe(role => {
      this.userRole = role;
      this.isLoggedIn = !!role;
      this.cdr.detectChanges();
    });
  }

  logout() {
    this.authService.logout();
    this.showLogoutPopup = false;
    this.router.navigate(['/login']);
  }
}

