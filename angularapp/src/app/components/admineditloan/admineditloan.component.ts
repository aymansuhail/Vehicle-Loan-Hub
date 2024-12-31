import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { LoanService } from 'src/app/services/loan.service';

declare var bootstrap: any;

@Component({
  selector: 'app-admineditloan',
  templateUrl: './admineditloan.component.html',
  styleUrls: ['./admineditloan.component.css']
})
export class AdmineditloanComponent implements OnInit {

  editLoanForm:FormGroup
  loanId!:number

  constructor(private loanService:LoanService,private fb:FormBuilder,private route:ActivatedRoute,private router:Router){}

  ngOnInit(): void {
    this.loanId = this.route.snapshot.params['id'];
    this.loadLoanData();
    this.editLoanForm = this.fb.group({
      loanType: ['', Validators.required],
      description: ['', Validators.required],
      interestRate: ['', [Validators.required, Validators.min(1)]],
      maximumAmount: ['', [Validators.required, Validators.min(1)]]
    });
  }
  loadLoanData(){
    this.loanService.getLoanById(this.loanId).subscribe({
      next: (loan) => {
        this.editLoanForm.patchValue(loan);
      },
      error: (err) => {
        this.showErrorMessage('Failed to load loan data.');
      }
    });
  }
  openConfirmationModal(){
    const confirmationModal = new bootstrap.Modal(document.getElementById('confirmationModal'));
    const confirmationMessage = document.getElementById('confirmationMessage');
    confirmationMessage.textContent = 'Are you sure you want to update this loan?';
    confirmationModal.show();
  }
  confirmEditLoan(){
    if (this.editLoanForm.valid){
      this.loanService.updateLoan(this.loanId, this.editLoanForm.value).subscribe({
        next: (data) => {
          this.showSuccessMessage('Loan updated successfully!');
        },
        error: (err) => {
          this.showErrorMessage('Failed to update loan. Please try again.');
        }
      });
    }
  }
  showSuccessMessage(message: string){
    const confirmationMessage = document.getElementById('confirmationMessage');
    confirmationMessage.textContent = message; this.autoCloseModal();
  }
  showErrorMessage(message: string){
    const confirmationMessage = document.getElementById('confirmationMessage');
    confirmationMessage.textContent = message; this.autoCloseModal();
  }
  autoCloseModal(){
    setTimeout(() => {
      const confirmationModal = bootstrap.Modal.getInstance(document.getElementById('confirmationModal'));
      confirmationModal.hide();
      this.router.navigate(['/admin/viewloans']);
    },
    1000);

  }
}
