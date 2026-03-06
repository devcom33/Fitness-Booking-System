import { CommonModule } from "@angular/common";
import { Component, inject, OnInit, signal } from "@angular/core";
import { ToastService } from "../../../../shared/services/toast-service";
import { AdminClientControllerService, ClientResponseDto, InstructorResponseDto } from "../../../../api";

@Component({
  selector: 'app-active-clients',
  imports: [CommonModule],
  templateUrl: './active-clients.html',
})
export class activeClients implements OnInit{
private adminService = inject(AdminClientControllerService);
  errorMsg = signal<string>('');
  clientsList = signal<ClientResponseDto[]>([]);
  private readonly toast = inject(ToastService);


  ngOnInit(): void {
    this.getActiveClients();
  }

  getActiveClients() {
    this.adminService.getClients().subscribe({
      next: (data) => this.clientsList.set(data),
      error: (err) => {
        this.errorMsg.set('Failed to load clients. Please check your connection.');
        console.error('[!] Fetch error:', err);
      },
    });
  }

  deactivateClient(clientId: string | undefined) {
    if (!clientId) {
      throw new Error('clientId is required');
    }
    this.adminService.toggleClientAccess(clientId, {userStatus: 'BLOCKED'} as any).subscribe({
      next: () => {
        this.toast.show('Client deactivated successfully!', 'success');
        this.getActiveClients();
      },
      error: (err) => {
        this.toast.show('Failed to deactivate client', 'error');
        console.error('[!] Deactivate error:', err);
      }
    });
  }

}