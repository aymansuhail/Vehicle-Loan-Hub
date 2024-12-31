import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Feedback } from 'src/app/models/feedback.model';
import { AuthService } from 'src/app/services/auth.service';
import { FeedbackService } from 'src/app/services/feedback.service';

@Component({
  selector: 'app-useraddfeedback',
  templateUrl: './useraddfeedback.component.html',
  styleUrls: ['./useraddfeedback.component.css']
})
export class UseraddfeedbackComponent implements OnInit {
  feedbackForm: FormGroup;
  showModal = false;
  userId: number;

  constructor(private fb: FormBuilder, private service: FeedbackService, private authService: AuthService, private router : Router) {}

  ngOnInit(): void {
    this.feedbackForm = this.fb.group({
      feedbackText: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(100)]]
    });
    this.userId = this.authService.getUserId();
  }

  onSubmit(): void {
    if (this.feedbackForm.valid) {
      const feedback: Feedback = {
        feedbackId: null,
        feedbackText: this.feedbackForm.get('feedbackText').value,
        date: new Date(),
        userId: this.userId
      };
      this.service.sendFeedback(feedback).subscribe(
        () => {
          this.showSuccessModal();
        },
        (error) => {
          console.error('Error submitting feedback', error);
          alert('An error occurred while submitting your feedback. Please try again.');
        }
      );
    } else {
      this.markFormGroupTouched(this.feedbackForm);
    }
  }

  private showSuccessModal(): void {
    this.showModal = true;
  }

  hideSuccessModal(): void {
    this.showModal = false;
    this.router.navigate(["/user/viewfeedbacks"])
  }

  private markFormGroupTouched(formGroup: FormGroup) {
    Object.keys(formGroup.controls).forEach(key => {
      const control = formGroup.get(key);
      control?.markAsTouched();
    });
  }
}
