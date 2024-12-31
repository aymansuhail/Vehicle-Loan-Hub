import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Loan } from 'src/app/models/loan.model';
import { LoanService } from 'src/app/services/loan.service';
declare var bootstrap: any;

@Component({
  selector: 'app-viewloan',
  templateUrl: './viewloan.component.html',
  styleUrls: ['./viewloan.component.css']
})
export class ViewloanComponent implements OnInit {
  allLoans: Loan[] = [];
  searchTerm: string = '';
  errorMessage: string | null = null;
  currentLoanId: number | null = null;
  loading: boolean = false; // Loader indicator
  selectedLoanDescription: string = ''; // To store the selected loan description

  constructor(private loanService: LoanService, private router: Router) { }

  ngOnInit(): void {
    this.getAllLoans();
  }

  getAllLoans() {
    this.loading = true;
    this.loanService.getAllLoans().subscribe(
      data => {
        this.allLoans = data;
        this.errorMessage = null;
        this.loading = false;
      },
      error => {
        this.errorMessage = `Error fetching loans: ${error.message}`;
        console.error('Error fetching loans:', error);
        this.loading = false;
      }
    );
  }

  edit(loanId: number) {
    this.router.navigate(['/admin/editloan', loanId]);
  }

  showDeleteConfirmation(loanId: number): void {
    this.currentLoanId = loanId;
    const confirmationModal = new bootstrap.Modal(document.getElementById('confirmationModal')!);
    confirmationModal.show();
  }

  deleteConfirmed(): void {
    if (this.currentLoanId !== null) {
      this.loading = true;
      this.loanService.deleteLoan(this.currentLoanId).subscribe(
        () => {
          this.allLoans = this.allLoans.filter(loan => loan.loanId !== this.currentLoanId);
          this.currentLoanId = null;
          const confirmationModal = bootstrap.Modal.getInstance(document.getElementById('confirmationModal')!);
          confirmationModal.hide();
          this.loading = false;
          this.showSuccessModal();
        },
        error => {
          if (error.status === 401) {
            this.errorMessage = 'Cannot delete the loan because a user has applied for it.';
          } else {
            this.errorMessage = `Error deleting loan: ${error.message}`;
          }
          console.error('Error deleting loan:', error);
          const confirmationModal = bootstrap.Modal.getInstance(document.getElementById('confirmationModal')!);
          confirmationModal.hide();
          this.showErrorModal();
          this.loading = false;
        }
      );
    }
  }

  showSuccessModal(): void {
    const successModal = new bootstrap.Modal(document.getElementById('successModal')!);
    successModal.show();
  }

  showErrorModal(): void {
    const errorModal = new bootstrap.Modal(document.getElementById('errorModal')!);
    errorModal.show();
  }

  viewFullDescription(loanId: number): void {
    const loan = this.allLoans.find(l => l.loanId === loanId);
    if (loan) {
      this.selectedLoanDescription = loan.description;
      const fullDescriptionModal = new bootstrap.Modal(document.getElementById('fullDescriptionModal')!);
      fullDescriptionModal.show();
    }
  }

  closeModal(): void {
    const fullDescriptionModal = bootstrap.Modal.getInstance(document.getElementById('fullDescriptionModal')!);
    fullDescriptionModal.hide();
  }
}
