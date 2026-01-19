import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthenticationControllerService, UserRegistrationRequestDto } from '../api';

@Component({
  selector: 'app-register',
  imports: [FormsModule],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {
  private authService = inject(AuthenticationControllerService);
  registerRequest: UserRegistrationRequestDto = { name: '', email: '', password: '' };

  register() {
    this.authService.register(this.registerRequest).subscribe({
      next: (res: any) => {
        localStorage.setItem('token', res.accessToken);
        console.log('about to navigate ...');
      },
    });
  }
}
