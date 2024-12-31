import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Loan } from 'src/app/models/loan.model';
import { LoanService } from 'src/app/services/loan.service';

declare var bootstrap: any;

@Component({
  selector: 'app-createloan',
  templateUrl: './createloan.component.html',
  styleUrls: ['./createloan.component.css']
})
export class CreateloanComponent implements OnInit {

  createLoanForm!: FormGroup;
  addLoans: Loan;
  
  constructor(
    private formBuilder: FormBuilder, 
    private service: LoanService, 
    private router: Router
  ) { }

  ngOnInit(): void {
    this.createLoanForm = this.formBuilder.group({
      loanType: ['', Validators.required],
      description: ['', Validators.required],
      interestRate: ['', [Validators.required, Validators.min(1)]],
      maximumAmount: ['', [Validators.required, Validators.min(1)]]
    });
  }

  openConfirmationModal() {
    const confirmationModal = new bootstrap.Modal(document.getElementById('confirmationModal'));
    const confirmationMessage = document.getElementById('confirmationMessage');
    confirmationMessage.textContent = 'Are you sure you want to create this loan?';
    confirmationModal.show();
  }

  confirmCreateLoan() {
    if (this.createLoanForm.valid) {
      this.service.addLoan(this.createLoanForm.value).subscribe({
        next: (data) => {
          this.addLoans = data;
          this.showSuccessMessage('Loan added successfully!');
         
        },
        error: (err) => {
          this.showErrorMessage('Failed to add loan. Please try again.');
        }
      });
    }
  }

  showSuccessMessage(message: string) {
    const confirmationMessage = document.getElementById('confirmationMessage');
    confirmationMessage.textContent = message;
    this.autoCloseModal();
  }

  showErrorMessage(message: string) {
    const confirmationMessage = document.getElementById('confirmationMessage');
    confirmationMessage.textContent = message;
    this.autoCloseModal();
  }

  autoCloseModal() {
    setTimeout(() => {
      const confirmationModal = bootstrap.Modal.getInstance(document.getElementById('confirmationModal'));
      confirmationModal.hide();
      this.router.navigate(["/admin/viewloans"])
    }, 1000); 

  }
}
