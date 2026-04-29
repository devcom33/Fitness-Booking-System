import { Component, OnInit, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { JsonPipe } from '@angular/common';
import { RRule } from 'rrule';

import { Navbar } from '../../../shared/components/navbar/navbar';
import { ToastService } from '../../../shared/services/toast-service';
import { FitnessFacadeService } from '../services/fitness-facade.service';
import { generateRRuleString, toIsoString } from '../utils/schedule.utils';

@Component({
  selector: 'app-fitness-class-stepper',
  standalone: true,
  imports: [Navbar, FormsModule, JsonPipe],
  templateUrl: './fitness-class-stepper.html',
})
export class FitnessClassStepper implements OnInit {
  private facade = inject(FitnessFacadeService);
  private toast = inject(ToastService);
  private router = inject(Router);

  currentStep = 1;
  isSubmitting = signal(false);
  categories = signal<string[]>([]);
  
  // Form State
  fitnessClassesDto = { name: '', description: '', durationMinutes: 0, capacity: 0, category: '' };
  scheduleClassDto = { fitnessClassId: '', startTime: '', endTime: '' };
  recurring = { startDateTime: '', endDateTime: '' };
  
  selectedRecurring: number | null = null;
  selectedDays: string[] = [];

  typeRecurringTime = [
    { code: null, option: 'None' },
    { code: RRule.DAILY, option: 'DAILY' },
    { code: RRule.WEEKLY, option: 'WEEKLY' },
  ];
  readonly WEEKLY = RRule.WEEKLY;

  ngOnInit() {
    this.facade.getCategories().subscribe(data => this.categories.set(data));
  }

  nextStep() {
    if (this.currentStep === 1) this.createFitnessClass();
    else if (this.currentStep < 3) this.currentStep++;
  }

  createFitnessClass() {
    this.isSubmitting.set(true);
    this.facade.createClass(this.fitnessClassesDto).subscribe({
      next: (data) => {
        this.scheduleClassDto.fitnessClassId = data.id!;
        this.currentStep++;
        this.isSubmitting.set(false);
      },
      error: () => {
        this.isSubmitting.set(false);
        this.toast.show('Failed to create class', 'error');
      }
    });
  }

  createClassSchedule() {
    if (this.isSubmitting()) return;

    const payload = {
      fitnessClassId: this.scheduleClassDto.fitnessClassId,
      startTime: toIsoString(this.scheduleClassDto.startTime),
      endTime: toIsoString(this.scheduleClassDto.endTime),
      templateDto: this.selectedRecurring !== null ? {
        rrule: generateRRuleString(this.selectedRecurring, this.selectedDays),
        startDateTime: toIsoString(this.recurring.startDateTime),
        endDateTime: toIsoString(this.recurring.endDateTime),
      } : undefined
    };

    this.isSubmitting.set(true);
    this.facade.createSchedule(payload).subscribe({
      next: () => {
        this.toast.show('Success!', 'success');
        setTimeout(() => this.router.navigate(['/dashboard']), 800);
      },
      error: () => this.isSubmitting.set(false)
    });
  }

  toggleDay(day: string) {
    const idx = this.selectedDays.indexOf(day);
    idx > -1 ? this.selectedDays.splice(idx, 1) : this.selectedDays.push(day);
  }

  previousStep() { if (this.currentStep > 1) this.currentStep--; }
  cancelForm() { this.router.navigate(['/dashboard']); }
}