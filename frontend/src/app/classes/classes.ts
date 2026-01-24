import { Component, inject, OnInit, signal } from '@angular/core';
import { ClassScheduleResponseDto, ClassSchedulesControllerService } from '../api';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-classes',
  imports: [DatePipe],
  templateUrl: './classes.html',
  styleUrl: './classes.css',
})
export class Classes implements OnInit {
  private classSchedulesService = inject(ClassSchedulesControllerService);
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
    throw new Error('Method not implemented.');
  }
}
