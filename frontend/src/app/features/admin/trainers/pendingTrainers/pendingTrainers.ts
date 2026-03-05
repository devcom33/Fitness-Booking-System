import { CommonModule } from "@angular/common";
import { Component, inject, OnInit, signal } from "@angular/core";
import { ToastService } from "../../../../shared/services/toast-service";
import { AdminControllerService, InstructorResponseDto } from "../../../../api";



@Component({
  selector: 'app-pending-trainers',
  imports: [CommonModule],
  templateUrl: './pendingTrainers.html',
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
        this.adminService.getPendingInstructor().subscribe({
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