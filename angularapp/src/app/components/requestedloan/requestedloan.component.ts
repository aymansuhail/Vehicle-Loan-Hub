import { Component, OnInit } from '@angular/core';
import { LoanService } from 'src/app/services/loan.service';
import { LoanApplication } from 'src/app/models/loanapplication.model';
import { AuthService } from 'src/app/services/auth.service';

declare var bootstrap: any;

@Component({
  selector: 'app-requestedloan',
  templateUrl: './requestedloan.component.html',
  styleUrls: ['./requestedloan.component.css']
})
export class RequestedloanComponent implements OnInit {
  requestedLoans: LoanApplication[] = [];
  searchTerm: string = '';
  filterStatus: string = '';
  selectedLoan: LoanApplication | null = null;
  loading: boolean = false; // Added loading state
  userName: string;
  loanToApproveOrReject: LoanApplication | null = null;

  constructor(private loanService: LoanService, private authService: AuthService) { }

  ngOnInit(): void {
    this.fetchRequestedLoans();
  }

  fetchRequestedLoans(): void {
    this.loading = true; // Start loading
    this.loanService.getAllLoanApplication().subscribe(
      (data: LoanApplication[]) => {
        this.requestedLoans = data;
        this.loading = false; // Stop loading
      },
      error => {
        console.error('Error fetching requested loans', error);
        this.loading = false; // Stop loading even on error
      }
    );
  }

  viewDetails(loan: LoanApplication): void {
    console.log('Viewing details for loan:', loan); // Log the loan data
    this.selectedLoan = loan;
    console.log('Selected loan:', this.selectedLoan); // Log the selected loan
    const modal = new bootstrap.Modal(document.getElementById('viewDetailsModal'));
    modal.show();
  }

  confirmApproveLoan(loanApplicationId: number): void {
    this.loanToApproveOrReject = this.requestedLoans.find(loan => loan.loanApplicationId === loanApplicationId);
    const modal = new bootstrap.Modal(document.getElementById('confirmApproveModal'));
    modal.show();
  }

  confirmRejectLoan(loanApplicationId: number): void {
    this.loanToApproveOrReject = this.requestedLoans.find(loan => loan.loanApplicationId === loanApplicationId);
    const modal = new bootstrap.Modal(document.getElementById('confirmRejectModal'));
    modal.show();
  }

  approveLoan(): void {
    if (this.loanToApproveOrReject) {
      const loanApplicationId = this.loanToApproveOrReject.loanApplicationId;
      this.loanService.updateLoanStatus(loanApplicationId.toString(), 1).subscribe(
        updatedLoan => {
          this.updateLoanStatus(loanApplicationId, 1);
          console.log('Loan approved successfully:', updatedLoan);
          this.loanToApproveOrReject = null; // Reset the selected loan
          const modal = bootstrap.Modal.getInstance(document.getElementById('confirmApproveModal'));
          modal.hide();
        },
        error => {
          console.error('Error approving loan', error);
        }
      );
    }
  }

  rejectLoan(): void {
    if (this.loanToApproveOrReject) {
      const loanApplicationId = this.loanToApproveOrReject.loanApplicationId;
      this.loanService.updateLoanStatus(loanApplicationId.toString(), -1).subscribe(
        updatedLoan => {
          this.updateLoanStatus(loanApplicationId, -1);
          console.log('Loan rejected successfully:', updatedLoan);
          this.loanToApproveOrReject = null; // Reset the selected loan
          const modal = bootstrap.Modal.getInstance(document.getElementById('confirmRejectModal'));
          modal.hide();
        },
        error => {
          console.error('Error rejecting loan', error);
        }
      );
    }
  }

  updateLoanStatus(loanApplicationId: number, status: number): void {
    const loan = this.requestedLoans.find(loan => loan.loanApplicationId === loanApplicationId);
    if (loan) {
      loan.loanStatus = status;
      this.requestedLoans = this.requestedLoans.map(l =>
        l.loanApplicationId === loanApplicationId ? { ...l, loanStatus: status } : l
      );
      console.log('Loan status updated successfully');
    }
  }

  getStatusText(status: number): string {
    switch (status) {
      case 1: return 'Approved';
      case 0: return 'Pending';
      case -1: return 'Rejected';
      default: return 'Unknown';
    }
  }
}
