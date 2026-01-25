import { Component, inject } from '@angular/core';
import { ToastService } from '../services/toast-service';

@Component({
  selector: 'app-toast-container',
  standalone: true,
  templateUrl: './toast-container.html',
  styles: [
    `
      @keyframes fade-in-left {
        from {
          opacity: 0;
          transform: translateX(20px);
        }
        to {
          opacity: 1;
          transform: translateX(0);
        }
      }
      .animate-fade-in-left {
        animation: fade-in-left 0.3s ease-out;
      }
    `,
  ],
})
export class ToastContainer {
  toastService = inject(ToastService);

  getStyles(type: string) {
    const base = 'text-white border-white/10 ';
    if (type === 'error') return base + 'bg-red-600/90';
    if (type === 'warning') return base + 'bg-[#F97316]/90';
    return base + 'bg-emerald-600/90';
  }
}
