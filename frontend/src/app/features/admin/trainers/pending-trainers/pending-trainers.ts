import { CommonModule } from "@angular/common";
import { Component, inject, OnInit, signal } from "@angular/core";
import { ToastService } from "../../../../shared/services/toast-service";
import { AdminControllerService, InstructorResponseDto } from "../../../../api";



@Component({
  selector: 'app-pending-trainers',
  imports: [CommonModule],
  templateUrl: './pending-trainers.html',
})
export class pendingTrainers implements OnInit{
  private adminService = inject(AdminControllerService);
  errorMsg = signal<string>('');
  pendingTrainingList = signal<InstructorResponseDto[]>([]);
  trainersList = signal<InstructorResponseDto[]>([]);
  private readonly toast = inject(ToastService);
  

  ngOnInit(): void {
    this.getPendingTrainers();
  }


  getPendingTrainers() {
        this.adminService.getInstructors("PENDING").subscribe({
        next: (data) => this.pendingTrainingList.set(data),
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
    this.adminService.approveInstructor(instructorId, {userStatus: 'ACTIVE'} as any).subscribe(
      {
      next: () => {
          this.toast.show('Trainer Approved Succcessfully!', 'success');
          this.getPendingTrainers()
      },
      }
    );
    
  }

  rejectTrainer(instructorId : string | undefined) {
    if (!instructorId) {
        throw new Error('InstructorId is required');
    }
    this.adminService.approveInstructor(instructorId, {userStatus: 'REJECTED'} as any).subscribe(
      {
      next: () => {
          this.toast.show('Trainer Rejected Succcessfully!', 'success');
          this.getPendingTrainers()
      },
      error: (err) => {
        this.toast.show('Failed to reject trainer', 'error');
        console.error('[!] Reject error:', err);
      }
      }
    );
  }

}