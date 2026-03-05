import { Injectable, signal } from '@angular/core';

export interface Toast {
  id: number;
  message: string;
  type: 'success' | 'error' | 'warning';
}

@Injectable({
  providedIn: 'root',
})
export class ToastService {
  toasts = signal<Toast[]>([]);

  show(message: string, type: Toast['type'] = 'success') {
    const id = Date.now();
    this.toasts.update((t) => [...t, { id, message, type }]);

    setTimeout(() => this.dimiss(id), 4000);
  }
  dimiss(id: number) {
    this.toasts.update((t) => t.filter((x) => x.id !== id));
  }
}
