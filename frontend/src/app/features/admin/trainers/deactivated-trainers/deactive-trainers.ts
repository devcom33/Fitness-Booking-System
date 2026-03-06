import { CommonModule } from "@angular/common";
import { Component, inject, OnInit, signal } from "@angular/core";
import { ToastService } from "../../../../shared/services/toast-service";
import { AdminInstructorControllerService, InstructorResponseDto } from "../../../../api";

@Component({
  selector: 'app-deactivated-trainers',
  imports: [CommonModule],
  templateUrl: './deactive-trainers.html',
})
export class deactiveTrainers implements OnInit{
private adminService = inject(AdminInstructorControllerService);
  errorMsg = signal<string>('');
  pendingTrainingList = signal<InstructorResponseDto[]>([]);
  trainersList = signal<InstructorResponseDto[]>([]);
  private readonly toast = inject(ToastService);
  activeTab = signal<'active' | 'pending'>('active');


  ngOnInit(): void {
    this.getActiveTrainers();
  }

  getActiveTrainers() {
    this.adminService.getInstructors("BLOCKED").subscribe({
      next: (data) => this.trainersList.set(data),
      error: (err) => {
        this.errorMsg.set('Failed to load bookings. Please check your connection.');
        console.error('[!] Fetch error:', err);
      },
    });
  }

  activateTrainer(instructorId: string | undefined) {
    if (!instructorId) {
      throw new Error('InstructorId is required');
    }
    this.adminService.toggleAccountAccess(instructorId, {userStatus: 'ACTIVE'} as any).subscribe({
      next: () => {
        this.toast.show('Trainer deactivated successfully!', 'success');
        this.getActiveTrainers();
      },
      error: (err) => {
        this.toast.show('Failed to deactivate trainer', 'error');
        console.error('[!] Deactivate error:', err);
      }
    });
  }

}