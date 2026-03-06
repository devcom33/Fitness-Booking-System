import { CommonModule } from "@angular/common";
import { Component, inject, OnInit, signal } from "@angular/core";
import { ToastService } from "../../../../shared/services/toast-service";
import { AdminClientControllerService, ClientResponseDto, InstructorResponseDto } from "../../../../api";

@Component({
  selector: 'app-deactivated-clients',
  imports: [CommonModule],
  templateUrl: './deactive-clients.html',
})
export class deactiveClients implements OnInit{
private adminService = inject(AdminClientControllerService);
  errorMsg = signal<string>('');
  clientsList = signal<ClientResponseDto[]>([]);
  private readonly toast = inject(ToastService);
  activeTab = signal<'active' | 'pending'>('active');


  ngOnInit(): void {
    this.getActiveClients();
  }

  getActiveClients() {
    this.adminService.getClients("BLOCKED").subscribe({
      next: (data) => this.clientsList.set(data),
      error: (err) => {
        this.errorMsg.set('Failed to load bookings. Please check your connection.');
        console.error('[!] Fetch error:', err);
      },
    });
  }

  activateTrainer(clientId: string | undefined) {
    if (!clientId) {
      throw new Error('InstructorId is required');
    }
    this.adminService.toggleClientAccess(clientId, {userStatus: 'ACTIVE'} as any).subscribe({
      next: () => {
        this.toast.show('Client deactivated successfully!', 'success');
        this.getActiveClients();
      },
      error: (err) => {
        this.toast.show('Failed to deactivate Client', 'error');
        console.error('[!] Deactivate error:', err);
      }
    });
  }

}