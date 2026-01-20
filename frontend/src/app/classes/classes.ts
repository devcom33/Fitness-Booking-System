import { Component, inject, OnInit, signal } from '@angular/core';
import { ClassScheduleRequestDto, ClassSchedulesControllerService } from '../api';

@Component({
  selector: 'app-classes',
  imports: [],
  templateUrl: './classes.html',
  styleUrl: './classes.css',
})
export class Classes implements OnInit {
  private classSchedulesService = inject(ClassSchedulesControllerService);
  classes = signal<ClassScheduleRequestDto[]>([]);
  errorMsg = signal<string>('');
  parsedClasses: any;

  ngOnInit() {
    this.loadClasses();
  }

  loadClasses() {
    this.classSchedulesService.getAllClassSchedules().subscribe({
      next: async (data) => {
        this.classes.set(data);
        console.log('[+] Classes : ', this.classes);
      },
      error: (err) => {
        this.errorMsg.set('Failed to load classes. Make sure you are logged in!');
        console.error(err);
      },
    });
  }
}
