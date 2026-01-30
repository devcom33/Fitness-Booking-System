import { Component, inject } from '@angular/core';
import { Navbar } from '../../components/navbar/navbar';
import {
  ClassScheduleRequestDto,
  ClassSchedulesControllerService,
  FitnessClassesControllerService,
  FitnessClassesDto,
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
  scheduleClassDto: ClassScheduleRequestDto = {
    fitnessClassId: '',
    instructorId: '',
    startTime: '',
    endTime: '',
  };

  nextStep() {
    if (this.currentStep == 1) {
      this.createClass();
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

  createClass() {
    this.fitnessClassService.createFitnessClass(this.fitnessClassesDto).subscribe({
      next: (data) => {
        console.log('data : ', data);
      },
    });
  }

  createClassSchedule() {
    this.scheduleClassService.createClassSchedules(this.scheduleClassDto).subscribe({
      next: (data) => {
        console.log('data : ', data);
      },
    });
  }
}
