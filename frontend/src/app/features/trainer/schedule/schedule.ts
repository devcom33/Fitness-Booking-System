import { Component, computed, effect, inject, OnInit, signal } from '@angular/core';
import { ClassSchedulesControllerService } from '../../../api';
import { toSignal } from '@angular/core/rxjs-interop';
import { Calendar } from '../../../shared/components/calendar/calendar';
import { Navbar } from '../../../shared/components/navbar/navbar';


@Component({
  selector: 'app-schedule',
  imports: [Calendar, Navbar],
  templateUrl: './schedule.html',
  styleUrl: './schedule.css',
})
export class Schedule {
  private readonly classSchedulesService = inject(ClassSchedulesControllerService);
  private rawClasses = toSignal(this.classSchedulesService.getAllClassSchedules(), {
    initialValue: [],
  });

  SCHEDULE_CLASSES = computed(() => {
    return this.rawClasses().map((c) => ({
      id: c.id,
      title: c.fitnessClassDto?.name,
      start: c.startTime,
      end: c.endTime,
    }));
  });

  constructor() {
    effect(() => {
      console.log('RAW CLASSES UPDATED:', this.rawClasses());
    });
  }

  /*SCHEDULE_CLASSES = computed<EventInput[]>(() =>
    this.classes().map((c) => ({
      id: c.id,
      title: c.fitnessClassDto?.name,
      start: c.startTime,
      end: c.endTime,
    })),
  );*/
}
