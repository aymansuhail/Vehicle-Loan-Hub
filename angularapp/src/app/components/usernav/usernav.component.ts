import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { LoanService } from 'src/app/services/loan.service';

@Component({
  selector: 'app-usernav',
  templateUrl: './usernav.component.html',
  styleUrls: ['./usernav.component.css']
})
export class UsernavComponent implements OnInit {
  allLoans: any[] = [];
  userId: number;

  constructor(private authService: AuthService, private router: Router, private loanService: LoanService) { }

  ngOnInit(): void {
    this.getLoans();
  }

  getLoans() {
    this.router.navigate(["/user/viewloans"])
  }


}
