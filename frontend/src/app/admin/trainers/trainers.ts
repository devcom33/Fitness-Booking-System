import { Component, inject, OnInit, signal } from '@angular/core';
import { Sidebar } from '../sidebar/sidebar';
import { CommonModule } from '@angular/common';
import { AdminControllerService, InstructorResponseDto } from '../../../openapi/src/app/api';
import { ToastService } from '../../services/toast-service';

@Component({
  selector: 'app-trainers',
  imports: [Sidebar, CommonModule],
  templateUrl: './trainers.html',
  styleUrl: './trainers.css',
})
export class Trainers implements OnInit{
  private adminService = inject(AdminControllerService);
  errorMsg = signal<string>('');
  pendingTrainingList = signal<InstructorResponseDto[]>([]);
  trainersList = signal<InstructorResponseDto[]>([]);
  private readonly toast = inject(ToastService);
  activeTabIndex = 0;
  

  ngOnInit(): void {
    this.getPendingTrainers();
    this.getTrainersList();
  }

  selectTab(index: number) {
    this.activeTabIndex = index;
  }

  getPendingTrainers() {
    this.adminService.getPendingInstructor().subscribe({
      next: (data) => this.pendingTrainingList.set(data),
      error: (err) => {
        this.errorMsg.set('Failed to load bookings. Please check your connection.');
        console.error('[!] Fetch error:', err);
      },
  });
  }

  getTrainersList() {
    this.adminService.getInstructors().subscribe({
      next: (data) => this.trainersList.set(data),
      error: (err) => {
        this.errorMsg.set('Failed to load bookings. Please check your connection.');
        console.error('[!] Fetch error:', err);
      },
    });
  }

  approveTrainer(instructorId : string | undefined) {
    if (!instructorId) {
        throw new Error('InstructorId is required');
      }
    this.adminService.updateInstructorStatus(instructorId, {userStatus: 'ACTIVE'} as any).subscribe(
      {
      next: () => {
          this.toast.show('Trainer Approved Succcessfully!', 'success');
      },
      }
    );
    
  }

  rejectTrainer(id: number) {
    console.log('Reject trainer:', id);
  }
}