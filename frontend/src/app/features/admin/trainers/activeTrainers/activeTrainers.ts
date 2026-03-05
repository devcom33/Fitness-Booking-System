import { CommonModule } from "@angular/common";
import { Component, inject, OnInit, signal } from "@angular/core";
import { ToastService } from "../../../../shared/services/toast-service";
import { AdminControllerService, InstructorResponseDto } from "../../../../api";

@Component({
  selector: 'app-active-trainers',
  imports: [CommonModule],
  templateUrl: './activeTrainers.html',
})
export class activeTrainers implements OnInit{
private adminService = inject(AdminControllerService);
  errorMsg = signal<string>('');
  pendingTrainingList = signal<InstructorResponseDto[]>([]);
  trainersList = signal<InstructorResponseDto[]>([]);
  private readonly toast = inject(ToastService);
  activeTab = signal<'active' | 'pending'>('active');


  ngOnInit(): void {
    this.getTrainersList();
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

}