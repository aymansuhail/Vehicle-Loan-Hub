import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Loan } from 'src/app/models/loan.model';
import { LoanService } from 'src/app/services/loan.service';
declare var bootstrap: any;

@Component({
  selector: 'app-userviewloan',
  templateUrl: './userviewloan.component.html',
  styleUrls: ['./userviewloan.component.css']
})
export class UserviewloanComponent implements OnInit {
  allLoans: Loan[] = [];
  searchTerm: string = '';
  loading: boolean = false; // Loader indicator
  errorMessage: string | null = null; // Error message
  selectedLoanDescription: string = ''; // To store the selected loan description
  showModal: boolean = false; // To toggle the modal display
  sortDirection: boolean = true; // True for ascending, False for descending

  constructor(private loanService: LoanService, private router: Router) { }

  ngOnInit(): void {
    this.getAllLoans();
  }

  getAllLoans() {
    this.loading = true; // Start loader
    this.loanService.getAllLoans().subscribe(
      data => {
        this.allLoans = data;
        this.loading = false; // Stop loader
      },
      error => {
        this.errorMessage = `Error fetching loans: ${error.message}`;
        console.error('Error fetching loans:', error);
        this.loading = false; // Stop loader
      }
    );
  }

  applyLoan(loan: Loan) {
    this.router.navigate(['/user/loanapplication', loan.loanId]);
  }

  viewFullDescription(loanId: number): void {
    const selectedLoan = this.allLoans.find(loan => loan.loanId === loanId);
    this.selectedLoanDescription = selectedLoan ? selectedLoan.description : '';
    const fullDescriptionModal = new bootstrap.Modal(document.getElementById('fullDescriptionModal')!);
    fullDescriptionModal.show();
  }

  closeModal(): void {
    const fullDescriptionModal = bootstrap.Modal.getInstance(document.getElementById('fullDescriptionModal')!);
    fullDescriptionModal.hide();
  }

  sortLoansByAmount(): void {
    this.sortDirection = !this.sortDirection; // Toggle sort direction
    this.allLoans.sort((a, b) => {
      if (this.sortDirection) {
        return a.maximumAmount - b.maximumAmount; // Ascending order
      } else {
        return b.maximumAmount - a.maximumAmount; // Descending order
      }
    });
  }
}
