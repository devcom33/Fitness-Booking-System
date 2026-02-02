import { Component, inject } from '@angular/core';
import { Navbar } from '../../components/navbar/navbar';
import {
  ClassScheduleRequestDto,
  ClassSchedulesControllerService,
  FitnessClassesControllerService,
  FitnessClassesDto,
  FitnessClassesResponseDto,
  RecurringScheduleTemplateDto,
} from '../../api';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-fitness-class-stepper',
  imports: [Navbar, FormsModule],
  templateUrl: './fitness-class-stepper.html',
  styleUrl: './fitness-class-stepper.css',
})
export class FitnessClassStepper {
  currentStep = 1;
  fitnessClassService = inject(FitnessClassesControllerService);
  scheduleClassService = inject(ClassSchedulesControllerService);
  fitnessClassesDto: FitnessClassesDto = {
    name: '',
    description: '',
    durationMinutes: 0,
    capacity: 0,
    category: '',
  };
  fitnessClassesResponseDto: FitnessClassesResponseDto = {
    id: '',
    name: '',
    description: '',
    durationMinutes: 0,
    capacity: 0,
    category: '',
  };

  scheduleClassDto: ClassScheduleRequestDto = {
    fitnessClassId: '',
    startTime: '',
    endTime: '',
  };

  payloadScheduleClassDto: ClassScheduleRequestDto = {
    fitnessClassId: this.scheduleClassDto.fitnessClassId,
    startTime: new Date(this.scheduleClassDto.startTime || new Date()).toISOString(),
    endTime: new Date(this.scheduleClassDto.endTime || new Date()).toISOString(),
  };

  steps = [
    { number: 1, label: 'Class Details' },
    { number: 2, label: 'Schedule' },
    { number: 3, label: 'Review' },
  ];

  nextStep() {
    if (this.currentStep == 1) {
      this.createFitnessClass();
    }
    if (this.currentStep < 3) {
      this.currentStep++;
    }
  }

  previousStep() {
    if (this.currentStep > 1) {
      this.currentStep--;
    }
  }

  createFitnessClass() {
    this.fitnessClassService.createFitnessClass(this.fitnessClassesDto).subscribe({
      next: (data) => {
        this.scheduleClassDto.fitnessClassId = data.id;
        console.log('data : ', data);
      },
    });
  }

  createClassSchedule() {
    const recurring: RecurringScheduleTemplateDto = {
      rrule: '',
      startDateTime: new Date().toISOString(),
      endDateTime: new Date().toISOString(),
    };
    const payload: ClassScheduleRequestDto = {
      fitnessClassId: this.scheduleClassDto.fitnessClassId,
      startTime: new Date(this.scheduleClassDto.startTime || new Date()).toISOString(),
      endTime: new Date(this.scheduleClassDto.endTime || new Date()).toISOString(),
      templateDto: recurring,
    };
    this.scheduleClassService.createClassSchedules(payload).subscribe({
      next: (data) => {
        console.log('data : ', data);
      },
    });
  }
}
