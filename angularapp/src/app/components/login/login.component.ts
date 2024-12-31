import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  errorMessage: string;
  loading: boolean = false;
  showErrorModal: boolean = false; // Modal state

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required,Validators.email,Validators.pattern('^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$')]],     
     password: ['', [Validators.required, Validators.minLength(8)]]
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      this.loading = true;
      this.authService.login(this.loginForm.value).subscribe(
        response => {
          console.log('Login successful', response);
          const userRole = this.authService.getUserRole();
          this.loading = false;
          if (userRole === 'ADMIN') {
            this.router.navigate(['/admin']);
          } else if (userRole === 'USER') {
            this.router.navigate(['/home']);
          }
        },
        error => {
          this.errorMessage = 'Invalid credentials. Please try again.';
          console.error('Login error:', error);
          this.loading = false;
          this.showErrorModal = true; // Show modal on error
        }
      );
    } else {
      console.log('Form is invalid');
      this.errorMessage = 'Please fill out the form correctly.';
      this.showErrorModal = true; // Show modal if form is invalid
    }
  }
}
