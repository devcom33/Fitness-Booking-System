import { Component, inject, OnInit, signal } from '@angular/core';
import { Sidebar } from '../sidebar/sidebar';
import { CommonModule } from '@angular/common';
import { ToastService } from '../../../services/toast-service';
import { activeTrainers } from "./activeTrainers/activeTrainers";
import { pendingTrainers } from "./pendingTrainers/pendingTrainers";
import { AdminControllerService, InstructorResponseDto } from '../../../api';

@Component({
  selector: 'app-trainers',
  imports: [Sidebar, CommonModule, activeTrainers, pendingTrainers],
  templateUrl: './trainers.html',
  styleUrl: './trainers.css',
})
export class Trainers implements OnInit{
  private adminService = inject(AdminControllerService);
  errorMsg = signal<string>('');
  pendingTrainingList = signal<InstructorResponseDto[]>([]);
  trainersList = signal<InstructorResponseDto[]>([]);
  private readonly toast = inject(ToastService);
  activeTab = signal<'active' | 'pending'>('active');

  // Methods to switch tabs
  showActive() { this.activeTab.set('active'); }
  showPending() { this.activeTab.set('pending'); }
  

  ngOnInit(): void {
  }


}