import { inject, Injectable } from '@angular/core';
import { 
  FitnessClassesControllerService, 
  ClassSchedulesControllerService, 
  CategoryControllerService 
} from '../../../api';

@Injectable({ providedIn: 'root' })
export class FitnessFacadeService {
  private classApi = inject(FitnessClassesControllerService);
  private scheduleApi = inject(ClassSchedulesControllerService);
  private categoryApi = inject(CategoryControllerService);

  getCategories() {
    return this.categoryApi.getCategories();
  }

  createClass(dto: any) {
    return this.classApi.createFitnessClass(dto);
  }

  createSchedule(payload: any) {
    return this.scheduleApi.createClassSchedules(payload);
  }
}