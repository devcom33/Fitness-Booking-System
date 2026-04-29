import { Component, OnInit, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RRule, Weekday } from 'rrule';
import {
  CategoryControllerService,
  ClassScheduleRequestDto,
  ClassSchedulesControllerService,
  FitnessClassesControllerService,
  FitnessClassesDto,
  RecurringScheduleTemplateDto,
} from '../../../api';
import { Navbar } from '../../../shared/components/navbar/navbar';
import { Router } from '@angular/router';
import { ToastService } from '../../../shared/services/toast-service';
import { JsonPipe } from '@angular/common';


@Component({
  selector: 'app-fitness-class-stepper',
  imports: [Navbar, FormsModule, JsonPipe],
  templateUrl: './fitness-class-stepper.html',
  styleUrl: './fitness-class-stepper.css',
})
export class FitnessClassStepper implements OnInit {
  readonly WEEKLY = RRule.WEEKLY;

  currentStep = 1;
  categories = signal<string[]>([]);

  private readonly toast = inject(ToastService);
  isSubmitting = signal(false);
  router = inject(Router);

  fitnessClassService = inject(FitnessClassesControllerService);
  scheduleClassService = inject(ClassSchedulesControllerService);
  categoryClassService = inject(CategoryControllerService);

  fitnessClassesDto: FitnessClassesDto = {
    name: '',
    description: '',
    durationMinutes: 0,
    capacity: 0,
    category: '',
  };

  recurring: RecurringScheduleTemplateDto = {
    rrule: '',
    startDateTime: '',
    endDateTime: '',
  };

  scheduleClassDto: ClassScheduleRequestDto = {
    fitnessClassId: '',
    startTime: '',
    endTime: '',
    templateDto: undefined,
  };

  selectedRecurring: number | null = null;
  selectedDays: string[] = [];
  rruleDays: Weekday[] = [];

  typeRecurringTime: Array<{ code: number | null; option: string }> = [
    { code: null, option: 'None' },
    { code: RRule.DAILY, option: 'DAILY' },
    { code: RRule.WEEKLY, option: 'WEEKLY' },
  ];

  DAY_MAP: Record<string, Weekday> = {
    Mon: RRule.MO,
    Tue: RRule.TU,
    Wed: RRule.WE,
    Thu: RRule.TH,
    Fri: RRule.FR,
    Sat: RRule.SA,
    Sun: RRule.SU,
  };

  ngOnInit(): void {
    this.loadCategories();
  }

  loadCategories(): void {
    this.categoryClassService.getCategories().subscribe({
      next: (data) => this.categories.set(data),
      error: (err) => console.error('[!] Fetch categories error:', err),
    });
  }

  nextStep(): void {
    if (this.currentStep === 1) {
      this.createFitnessClass();
      return;
    }
    if (this.currentStep < 3) this.currentStep++;
  }

  previousStep(): void {
    if (this.currentStep > 1) this.currentStep--;
  }

  createFitnessClass(): void {
    this.isSubmitting.set(true);
    this.fitnessClassService.createFitnessClass(this.fitnessClassesDto).subscribe({
      next: (data) => {
        
        console.log('API Response:', data);
        
        if (!data?.id) {
          console.error('Missing ID in response:', data);
          this.toast.show('Error: Class ID not received', 'error');
          return;
        }
        this.scheduleClassDto.fitnessClassId = data.id;
        this.isSubmitting.set(false);
        this.currentStep++;
      },
      error: (err) => {
        this.isSubmitting.set(false);
        console.error('Error creating class:', err);
        this.toast.show('Failed to create class', 'error');
      },
    });
  }

  toggleDay(day: string): void {
    const index = this.selectedDays.indexOf(day);
    if (index > -1) this.selectedDays.splice(index, 1);
    else this.selectedDays.push(day);
  }

  toRrule(days: string[]): void {
    this.rruleDays = days
      .map((d) => this.DAY_MAP[d])
      .filter((d): d is Weekday => !!d);
  }

  createClassSchedule(): void {
    if (this.isSubmitting()) return;
    
    if (!this.scheduleClassDto.fitnessClassId) {
      console.error('Missing class id');
      return;
    }

    if (!this.scheduleClassDto.startTime || !this.scheduleClassDto.endTime) {
      console.error('Start/End time is required');
      return;
    }

    let templateDto: RecurringScheduleTemplateDto | undefined = undefined;

    if (this.selectedRecurring !== null) {
      const options: any = { freq: this.selectedRecurring };

      if (this.selectedRecurring === RRule.WEEKLY) {
        this.toRrule(this.selectedDays);
        if (!this.rruleDays.length) {
          console.error('Select at least one weekday for weekly recurrence');
          return;
        }
        options.byweekday = this.rruleDays;
      }

      const rule = new RRule(options);
      const ruleText = rule.toString().replace('RRULE:', '');

      if (!this.recurring.startDateTime || !this.recurring.endDateTime) {
        console.error('Recurring start/end date is required');
        return;
      }

      templateDto = {
        rrule: ruleText,
        startDateTime: new Date(this.recurring.startDateTime).toISOString(),
        endDateTime: new Date(this.recurring.endDateTime).toISOString(),
      };
    }

    const payload: ClassScheduleRequestDto = {
      fitnessClassId: this.scheduleClassDto.fitnessClassId,
      startTime: new Date(this.scheduleClassDto.startTime).toISOString(),
      endTime: new Date(this.scheduleClassDto.endTime).toISOString(),
      templateDto,
    };
    this.isSubmitting.set(true);
    this.scheduleClassService.createClassSchedules(payload).subscribe({
      next: (data) => {
        this.toast.show('Class Created Successfully!', 'success');
        this.isSubmitting.set(false);
        setTimeout(() => this.router.navigate(['/dashboard']), 800);
      },
      error: (err) => {
        console.error('Error creating schedule:', err);
        this.isSubmitting.set(false);
        this.toast.show('Failed to create class', 'error');
      },
    });
  }


  cancelForm(): void {
  this.router.navigate(['/dashboard']);

}
}