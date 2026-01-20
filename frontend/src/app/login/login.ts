import { Component, inject } from '@angular/core';
import { AuthenticationControllerService, AuthRequestDto } from '../api';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

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

  login() {
    localStorage.removeItem('token');
    this.authService.login(this.authRequest).subscribe({
      next: (res: any) => {
        localStorage.setItem('token', res.accessToken);
        console.log('about to navigate ...');
        this.router.navigate(['/dashboard']);
      },
    });
  }
}
