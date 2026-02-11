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
import { RRule, Weekday } from 'rrule';

@Component({
  selector: 'app-fitness-class-stepper',
  imports: [Navbar, FormsModule],
  templateUrl: './fitness-class-stepper.html',
  styleUrl: './fitness-class-stepper.css',
})
export class FitnessClassStepper {
  readonly WEEKLY = RRule.WEEKLY;
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
  recurring: RecurringScheduleTemplateDto = {
    rrule: '',
    startDateTime: new Date().toISOString(),
    endDateTime: new Date().toISOString(),
  };

  scheduleClassDto: ClassScheduleRequestDto = {
    fitnessClassId: '',
    startTime: '',
    endTime: '',
    templateDto: this.recurring,
  };

  steps = [
    { number: 1, label: 'Class Details' },
    { number: 2, label: 'Schedule' },
    { number: 3, label: 'Review' },
  ];

  selectedDays: string[] = [];

  toggleDay(day: string): void {
    const index = this.selectedDays.indexOf(day);
    if (index > -1) {
      this.selectedDays.splice(index, 1);
    } else {
      this.selectedDays.push(day);
    }
  }
  rruleDays : Weekday[] = [];
  toRrule(selectedDays: string[])
  {
    for (let selectday of selectedDays)
    {
    switch (selectday)
    {
      case "Mon":
        this.rruleDays.push(RRule.MO);
        break
      case "Tue":
        this.rruleDays.push(RRule.TU);
        break
      case "Wed":
        this.rruleDays.push(RRule.WE);
        break
      case "Thu":
        this.rruleDays.push(RRule.TH);
        break
      case "Fri":
        this.rruleDays.push(RRule.FR);
        break
      case "Sat":
        this.rruleDays.push(RRule.SA);
        break
      case "Sun":
        this.rruleDays.push(RRule.SU);
        break
      default:
        this.rruleDays.push(RRule.MO);
    }
    }
  }
  selectedRecurring = RRule.DAILY;
  typeRecurringTime = [
    {'code': null, 'option': "None"},
    {'code': RRule.DAILY, 'option': "DAILY" },
    {'code': RRule.WEEKLY, 'option': "WEEKLY"}
  ] ; 
  nextStep() {
    if (this.currentStep == 1) {
      //this.createFitnessClass();
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
    let rule: RRule | null = null;

    // Build the Rule
    const options: any = { freq: this.selectedRecurring };

    if (this.selectedRecurring === RRule.WEEKLY) {
      this.toRrule(this.selectedDays);
      options.byweekday = this.rruleDays;
      rule = new RRule(options);
    } else if (this.selectedRecurring === RRule.DAILY) {
      rule = new RRule(options);
    }

    if (!rule) {
      console.error("No recurrence rule generated.");
      return;
    }

    // Format the string by Removing 'RRULE:' prefix
    const ruleString = rule.toString();
    const ruleFormatted = ruleString.includes(':') 
      ? ruleString.split(':')[1] 
      : ruleString;

    // Construct Payload
    const recurring: RecurringScheduleTemplateDto = {
      rrule: ruleFormatted,
      startDateTime: new Date(this.recurring?.startDateTime ?? new Date()).toISOString(),
      endDateTime: new Date(this.recurring?.endDateTime ?? new Date()).toISOString(),
    };

    const payload: ClassScheduleRequestDto = {
      fitnessClassId: this.scheduleClassDto.fitnessClassId,
      startTime: new Date(this.scheduleClassDto.startTime ?? new Date()).toISOString(),
      endTime: new Date(this.scheduleClassDto.endTime ?? new Date()).toISOString(),
      templateDto: recurring,
    };

    console.log("Payload Prepared:", payload);

    // API Call
    this.scheduleClassService.createClassSchedules(payload).subscribe({
      next: (data) => console.log('Success:', data),
      error: (err) => console.error('Error creating schedule:', err)
    });
  }


}
