import { Component, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth-service';
import { Sidebar } from "../../../features/admin/sidebar/sidebar";
import { Navbar } from '../navbar/navbar';

@Component({
  selector: 'app-dashboard',
  imports: [RouterLink, Navbar, Sidebar],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard {
  authService = inject(AuthService);

  role = this.authService.getUserRoles();
}
