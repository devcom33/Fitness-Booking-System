import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  imports: [CommonModule, RouterModule],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.css',
})
export class Sidebar {
  menuItems = [
    { icon: '', label: 'Manage Trainers', route: '/admin/trainers' },
    { icon: '', label: 'Manage Clients', route: '/admin/clients' },
    { icon: '', label: 'Manage Classes', route: '/admin/classes' },
    { icon: '', label: 'Reports', route: '/admin/reports' },
    { icon: '', label: 'Settings', route: '/admin/settings' },
  ];
}
