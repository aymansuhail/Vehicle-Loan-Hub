import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { LoanService } from 'src/app/services/loan.service';
import { AuthService } from 'src/app/services/auth.service';

declare var bootstrap: any;

@Component({
  selector: 'app-loanform',
  templateUrl: './loanform.component.html',
  styleUrls: ['./loanform.component.css']
})
export class LoanformComponent implements OnInit {
  loanForm: FormGroup;
  submitted = false;
  currentDate: string = new Date().toISOString().split('T')[0];
  userId: number;
  loanId: number;
  selectedFile: File | null = null;
  fileErrorMessage: string = '';

  constructor(
    private router: Router,
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private loanService: LoanService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loanId = +this.route.snapshot.paramMap.get('loanId');
    if (isNaN(this.loanId)) {
      console.error('Invalid loan ID');
    } else {
      console.log('Loan ID:', this.loanId);
    }

    this.userId = this.authService.getUserId();
    if (this.userId === null) {
      console.error('User ID not found');
    } else {
      console.log('User ID:', this.userId);
    }

    this.loanForm = this.fb.group({
      income: ['', [Validators.required, Validators.min(1)]],
      model: ['', [Validators.required, this.pastDateValidator.bind(this)]],
      purchasePrice: ['', [Validators.required, Validators.min(1)]],
      address: ['', [Validators.required, Validators.minLength(10)]],
      proof: [null, Validators.required]
    });
  }

  pastDateValidator(control: any): { [key: string]: boolean } | null {
    const selectedDate = new Date(control.value);
    const today = new Date();
    return selectedDate > today ? { futureDate: true } : null;
  }

  onFileChange(event: any): void {
    const file = event.target.files[0];
    if (file) {
      if (file.size > 20000000) { // 2 MB file size limit
        this.fileErrorMessage = 'File size exceeds 2 MB';
        this.selectedFile = null;
        this.loanForm.get('proof')?.setErrors({ fileInvalid: true });
      } else {
        this.selectedFile = file;
        this.fileErrorMessage = '';
        this.loanForm.get('proof')?.setValue(file);
        this.loanForm.get('proof')?.updateValueAndValidity();
      }
    }
  }

  openConfirmationModal(): void {
    if (this.loanForm.valid && this.selectedFile) {
      const confirmationModal = new bootstrap.Modal(document.getElementById('confirmationModal'));
      const confirmationMessage = document.getElementById('confirmationMessage');
      confirmationMessage.textContent = 'Are you sure you want to create this loan?';
      confirmationModal.show();
    } else {
      this.submitted = true;
      if (this.loanForm.invalid) {
        alert('All fields are required');
      }
    }
  }

  confirmCreateLoan(): void {
    if (this.loanForm.valid && this.selectedFile) {
      const reader = new FileReader();
      reader.onloadend = () => {
        const base64String = reader.result as string;

        const loanApplication = {
          ...this.loanForm.value,
          userId: this.userId,
          loanId: this.loanId,
          submissionDate: this.currentDate,
          loanStatus: 0,
          file: base64String.split(',')[1]
        };

        this.loanService.addLoanApplication(loanApplication).subscribe(
          response => {
            this.showSuccessMessage('Loan added successfully!');
            this.closeConfirmationModal();
          },
          error => {
            this.showErrorMessage('Failed to add loan. Please try again.');
            this.closeConfirmationModal();
          }
        );
      };
      reader.readAsDataURL(this.selectedFile);
    }
  }

  closeConfirmationModal(): void {
    const confirmationModalElement = document.getElementById('confirmationModal');
    if (confirmationModalElement) {
      const confirmationModal = bootstrap.Modal.getInstance(confirmationModalElement);
      if (confirmationModal) {
        confirmationModal.hide();
      }
    }
  }

  showSuccessMessage(message: string): void {
    const resultMessage = document.getElementById('resultMessage');
    if (resultMessage) {
      resultMessage.textContent = message;
    }
    this.autoCloseModal();
  }

  showErrorMessage(message: string): void {
    const resultMessage = document.getElementById('resultMessage');
    if (resultMessage) {
      resultMessage.textContent = message;
    }
    this.autoCloseModal();
  }

  autoCloseModal(): void {
    setTimeout(() => {
      const resultModalElement = document.getElementById('resultModal');
      if (resultModalElement) {
        const resultModal = bootstrap.Modal.getInstance(resultModalElement);
        if (resultModal) {
          resultModal.hide();
        }
      }
      this.router.navigate(['/user/userappliedloan']);
    }, 1000);
  }
}
