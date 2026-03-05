import { Component, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Navbar } from '../../components/navbar/navbar';
import { AuthService } from '../../services/AuthService';
import { Sidebar } from "../../features/admin/sidebar/sidebar";

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
