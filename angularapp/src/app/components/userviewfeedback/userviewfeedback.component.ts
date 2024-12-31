import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { FeedbackService } from 'src/app/services/feedback.service';
import { Feedback } from 'src/app/models/feedback.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-userviewfeedback',
  templateUrl: './userviewfeedback.component.html',
  styleUrls: ['./userviewfeedback.component.css']
})
export class UserviewfeedbackComponent implements OnInit {
  feedbacks: Feedback[] = [];
  showDeleteModal = false;
  currentFeedbackId: number | null = null;
  errorMessage: string | null = null;
  loading: boolean = false; // Added loading state

  constructor(private service: FeedbackService, private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.loadFeedbacks();
  }

  loadFeedbacks(): void {
    const userId = this.authService.getUserId();
    if (userId) {
      this.loading = true; // Start loading
      this.service.getAllFeedbacksByUserId(userId).subscribe(
        (data) => {
          this.feedbacks = data;
          this.errorMessage = null;
          this.loading = false; // Stop loading
        },
        (error) => {
          if (error.status !== 500) {
            this.errorMessage = `Error fetching feedbacks: ${error.message}`;
          }
          console.error('Error fetching feedbacks', error);
          this.loading = false; // Stop loading even on error
        }
      );
    } else {
      this.errorMessage = 'User ID not found.';
    }
  }

  confirmDelete(feedbackId: number): void {
    this.currentFeedbackId = feedbackId;
    this.showDeleteModal = true;
    this.router.navigate(["/user/viewfeedbacks"]);
  }

  hideDeleteModal(): void {
    this.showDeleteModal = false;
    this.currentFeedbackId = null;
    this.router.navigate(["/user/viewfeedbacks"]);
  }

  deleteConfirmed(): void {
    if (this.currentFeedbackId !== null) {
      this.service.deleteFeedback(this.currentFeedbackId).subscribe(
        () => {
          this.feedbacks = this.feedbacks.filter(feedback => feedback.feedbackId !== this.currentFeedbackId);
          this.hideDeleteModal();
        },
        (error) => {
          console.error('Error deleting feedback', error);
        }
      );
    }
  }
}
