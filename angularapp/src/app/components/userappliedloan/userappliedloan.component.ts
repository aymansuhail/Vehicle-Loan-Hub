import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LoanApplication } from 'src/app/models/loanapplication.model';
import { AuthService } from 'src/app/services/auth.service';
import { LoanService } from 'src/app/services/loan.service';

declare var bootstrap: any;

@Component({
  selector: 'app-userappliedloan',
  templateUrl: './userappliedloan.component.html',
  styleUrls: ['./userappliedloan.component.css']
})
export class UserappliedloanComponent implements OnInit {

  userAppliedLoans: LoanApplication[] = [];
  searchText: string = '';
  selectedApplicationId: number | null = null;
  loading: boolean = false; // Added loading state

  constructor(
    private loanService: LoanService, 
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService
  ) {}

  getAllApplicationsForUserId() {
    const userId = this.authService.getUserId();
    this.loading = true; // Start loading
    this.loanService.getAppliedLoans(userId).subscribe((data) => {
      this.userAppliedLoans = data;
      console.log(data);
      this.loading = false; // Stop loading
    }, error => {
      console.error('Error fetching applied loans', error);
      this.loading = false; // Stop loading even on error
    });
  }

  ngOnInit(): void {
    this.getAllApplicationsForUserId();
  }

  openDeleteModal(applicationId: number) {
    this.selectedApplicationId = applicationId;
    console.log('Opening Delete Modal with Application ID:', this.selectedApplicationId); // Log to ensure it's set correctly
    const deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));
    deleteModal.show();
  }

  confirmDeleteApplication() {
    console.log('Confirm Delete Application with ID:', this.selectedApplicationId); // Log to check ID before deletion
    if (this.selectedApplicationId !== null) {
      this.loanService.deleteLoanApplication(this.selectedApplicationId).subscribe({
        next: () => {
          this.userAppliedLoans = this.userAppliedLoans.filter(app => app.loanApplicationId !== this.selectedApplicationId);
          this.selectedApplicationId = null;
          this.closeModal('deleteModal'); // Close the confirmation modal
          this.showSuccessMessage('Loan application deleted successfully.');
        },
        error: (error) => {
          console.error('Error deleting loan application:', error);
          this.showErrorMessage('Failed to delete loan application.');
        }
      });
    } else {
      console.error('Selected application ID is null or undefined.');
    }
  }

  closeModal(modalId: string) {
    const modalElement = document.getElementById(modalId);
    if (modalElement) {
      const modalInstance = bootstrap.Modal.getInstance(modalElement);
      if (modalInstance) {
        modalInstance.hide();
      } else {
        console.error(`${modalId} instance not found.`);
      }
    } else {
      console.error(`${modalId} element not found.`);
    }
  }

  showSuccessMessage(message: string) {
    const resultMessage = document.getElementById('resultMessage');
    if (resultMessage) {
      resultMessage.textContent = message;
    } else {
      console.error('resultMessage element not found.');
    }

    // Update result modal footer to show only the Close button
    const resultModalElement = document.getElementById('resultModal');
    const resultModalFooter = resultModalElement?.querySelector('.modal-footer');
    if (resultModalFooter) {
      resultModalFooter.innerHTML = `
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
      `;
    }

    this.openResultModal();
  }

  showErrorMessage(message: string) {
    const resultMessage = document.getElementById('resultMessage');
    if (resultMessage) {
      resultMessage.textContent = message;
    } else {
      console.error('resultMessage element not found.');
    }
    this.openResultModal();
  }

  openResultModal() {
    const resultModalElement = document.getElementById('resultModal');
    if (resultModalElement) {
      const resultModal = new bootstrap.Modal(resultModalElement);
      resultModal.show();
    } else {
      console.error('resultModal element not found.');
    }
  }

  autoCloseModal() {
    setTimeout(() => {
      this.closeModal('deleteModal');
      this.closeModal('resultModal');
      this.router.navigate(['/userviewloan']);
    }, 1000); // Adjust the timeout duration as needed
  }

  getStatusText(status: number): string {
    switch (status) {
      case 1:
        return 'Accepted';
      case 0:
        return 'Pending';
      case -1:
        return 'Rejected';
      default:
        return 'Unknown';
    }
  }
}
