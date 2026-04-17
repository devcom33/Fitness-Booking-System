import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import {
  AuthenticationControllerService,
  InstructorApplicationRequest,
  RoleDto,
  UserRegistrationRequestDto,
} from '../../../api';
import { Router } from '@angular/router';
import { ToastService } from '../../services/toast-service';

@Component({
  selector: 'app-register',
  imports: [FormsModule],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {
  private authService = inject(AuthenticationControllerService);
  private router = inject(Router);
  private readonly toast = inject(ToastService);

  RoleName = RoleDto;

  instructorBio = '';
  instructorSpecialization = '';

  registerRequest: UserRegistrationRequestDto = {
    name: '',
    email: '',
    password: '',
    roleDto: {
      name: this.RoleName.NameEnum.Client,
    },
  };

  register() {
    const selectedRole = this.registerRequest.roleDto?.name;

    if (selectedRole === this.RoleName.NameEnum.Instructor) {
      const instructorRequest: InstructorApplicationRequest = {
        name: this.registerRequest.name ?? '',
        email: this.registerRequest.email ?? '',
        password: this.registerRequest.password ?? '',
        roleDto: { name: this.RoleName.NameEnum.Instructor },
        bio: this.instructorBio ?? '',
        specialization: this.instructorSpecialization ?? '',
      };

      this.authService.applyAsInstructor(instructorRequest).subscribe({
        next: () => {
          this.toast.show('Application submitted. Awaiting admin approval.', 'success');
          this.router.navigate(['/application-under-review']);
        },
        error: (err) => {
          console.error(err);
          this.toast.show('Failed to submit instructor application.', 'error');
        },
      });
      return;
    }

    this.authService.register(this.registerRequest).subscribe({
      next: (res: any) => {
        localStorage.setItem('token', res.accessToken);
        this.toast.show('Account created successfully!', 'success');
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        console.error(err);
        const code = err?.error?.code;

        if (code === 'EMAIL_ALREADY_EXISTS') {
          this.toast.show('Email already exists. Try another one.', 'warning');
          return;
        }

        this.toast.show('Registration failed. Please try again.', 'error');
      },
    });
  }
}