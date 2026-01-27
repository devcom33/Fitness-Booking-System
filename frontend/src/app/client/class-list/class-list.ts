import { Component, inject, OnInit, signal, DestroyRef } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { DatePipe } from '@angular/common';
import {
  BookingControllerService,
  ClassScheduleResponseDto,
  ClassSchedulesControllerService,
} from '../../api';
import { ToastService } from '../../services/toast-service';

@Component({
  selector: 'app-class-list',
  standalone: true,
  imports: [DatePipe],
  templateUrl: './class-list.html',
  styleUrl: './class-list.css',
})
export class ClassList implements OnInit {
  private readonly classSchedulesService = inject(ClassSchedulesControllerService);
  private readonly bookingService = inject(BookingControllerService);
  private readonly destroyRef = inject(DestroyRef);
  private readonly toast = inject(ToastService);

  // State signals
  classes = signal<ClassScheduleResponseDto[]>([]);
  errorMsg = signal<string>('');

  ngOnInit(): void {
    this.loadClasses();
  }

  loadClasses(): void {
    this.classSchedulesService
      .getAllClassSchedules()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (data) => this.classes.set(data),
        error: (err) => {
          this.errorMsg.set('Failed to load classes. Please check your connection.');
          console.error('[!] Fetch error:', err);
        },
      });
  }

  bookClass(scheduleId: string | undefined): void {
    if (!scheduleId) {
      console.error('Cannot book: Schedule ID is missing');
      return;
    }

    this.bookingService.createBooking({ classScheduleId: scheduleId }).subscribe({
      next: () => this.toast.show('Booking Confirmed!', 'success'),
      error: (err) => {
        switch (err.status) {
          case 400:
            this.toast.show('This class is full! Try another time slot.', 'error');
            break;
          case 409:
            this.toast.show('You already have a spot in this class.', 'warning');
            break;
          case 401:
            this.toast.show('Please log in to book a class.', 'warning');
            break;
          default:
            this.toast.show('Something went wrong. Please try again later.', 'error');
        }
      },
    });
  }
}
