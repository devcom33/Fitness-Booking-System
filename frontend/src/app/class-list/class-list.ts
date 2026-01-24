import { Component, inject, signal } from '@angular/core';
import {
  BookingControllerService,
  ClassScheduleResponseDto,
  ClassSchedulesControllerService,
} from '../api';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-class-list',
  imports: [DatePipe],
  templateUrl: './class-list.html',
  styleUrl: './class-list.css',
})
export class ClassList {
  private classSchedulesService = inject(ClassSchedulesControllerService);
  private bookingService = inject(BookingControllerService);
  classes = signal<ClassScheduleResponseDto[]>([]);
  errorMsg = signal<string>('');

  ngOnInit() {
    this.loadClasses();
  }

  loadClasses() {
    this.classSchedulesService.getAllClassSchedules().subscribe({
      next: (data) => {
        this.classes.set(data);
        console.log('[+] Classes loaded successfully');
      },
      error: (err) => {
        this.errorMsg.set('Failed to load classes. Make sure you are logged in!');
        console.error(err);
      },
    });
  }

  bookClass() {
    //this.bookingService.createBooking().subscribe({});
    throw new Error('Method not implemented.');
  }
}
