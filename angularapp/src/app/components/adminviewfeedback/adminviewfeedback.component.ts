import { Component, OnInit } from '@angular/core';
import { Feedback } from 'src/app/models/feedback.model';
import { FeedbackService } from 'src/app/services/feedback.service';
import { User } from 'src/app/models/user.model';
declare var bootstrap: any;
 
@Component({
  selector: 'app-adminviewfeedback',
  templateUrl: './adminviewfeedback.component.html',
  styleUrls: ['./adminviewfeedback.component.css']
})
export class AdminviewfeedbackComponent implements OnInit {
  feedbacks: Feedback[] = [];
  selectedUser: User | null = null;
  showUserModal = false;
searchTerm: any;
loading: any;
 
  constructor(private feedbackService: FeedbackService) {}
 
  ngOnInit(): void {
    this.getFeedbacks();
  }
 
  getFeedbacks() {
    this.feedbackService.getFeedbacks().subscribe(
      (data) => {
        this.feedbacks = data;
      },
      (error) => {
        console.error('Error fetching feedbacks', error);
      }
    );
  }
 
  showUserDetails(user: User): void {
    this.selectedUser = user;
    const userModal = new bootstrap.Modal(document.getElementById('userModal')!);
    userModal.show();
  }
 
  hideUserModal(): void {
    const userModal = bootstrap.Modal.getInstance(document.getElementById('userModal')!);
    userModal.hide();
    this.selectedUser = null;
  }
}