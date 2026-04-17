import { Component, inject } from '@angular/core';
import { AuthenticationControllerService, AuthRequestDto } from '../../../api';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ToastService } from '../../services/toast-service';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  private authService = inject(AuthenticationControllerService);
  private router = inject(Router);
  authRequest: AuthRequestDto = { email: '', password: '' };
  errorMsg: Array<String> = [];
  private readonly toast = inject(ToastService);

  login() {
    localStorage.removeItem('token');
    this.authService.login(this.authRequest).subscribe({
      next: (res: any) => {
        localStorage.setItem('token', res.accessToken);
        this.toast.show('Welcome back!', 'success');
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        const backendCode = err?.error?.code;

        if (backendCode === 'ACCOUNT_PENDING_APPROVAL') {
          this.toast.show('Your instructor application is under review.', 'warning');
          this.router.navigate(['/application-under-review']);
          return;
        }

        if (backendCode === 'INVALID_CREDENTIALS') {
          this.toast.show('Invalid email or password.', 'error');
          return;
        }

        this.toast.show('Login failed. Please try again.', 'error');
      },
    });
  }
}
