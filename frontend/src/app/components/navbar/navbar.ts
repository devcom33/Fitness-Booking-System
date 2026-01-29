import { Component, inject } from '@angular/core';
import { AuthService } from '../../services/AuthService';

@Component({
  selector: 'app-navbar',
  imports: [],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {
  authService = inject(AuthService);

  onLogout() {
    this.authService.logout();
  }
}
