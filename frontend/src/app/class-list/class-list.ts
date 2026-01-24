import { Component, inject, OnInit, signal, DestroyRef } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { DatePipe } from '@angular/common';
import {
  AuthenticationControllerService,
  BookingControllerService,
  ClassScheduleResponseDto,
  ClassSchedulesControllerService,
} from '../api';
import { jwtDecode } from 'jwt-decode';

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

  // State signals
  classes = signal<ClassScheduleResponseDto[]>([]);
  errorMsg = signal<string>('');
  token = localStorage.getItem('token');

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
    if (this.token) {
      const decodedToken: any = jwtDecode(this.token);
      console.log('Decoded token:', decodedToken);
    }
    if (!scheduleId) {
      console.error('Cannot book: Schedule ID is missing');
      return;
    }

    this.bookingService.createBooking({ classScheduleId: scheduleId }).subscribe({
      next: () => alert('Booked!'),
      error: (err) => console.error(err),
    });
  }
}
