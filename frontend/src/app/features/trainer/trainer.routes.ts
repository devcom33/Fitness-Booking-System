import { Routes } from "@angular/router";
import { authGuard } from "../../core/guards/auth-guard";
import { FitnessClassStepper } from "./fitness-class-stepper/fitness-class-stepper";
import { Schedule } from "./schedule/schedule";



export const trainerRoutes: Routes = [
  {
    path: 'schedule',
    component: Schedule,
    canActivate: [authGuard],
    data: { expectedRole: 'ROLE_INSTRUCTOR' },
  },
  {
    path: 'create-class',
    component: FitnessClassStepper,
    canActivate: [authGuard],
    data: { expectedRole: 'ROLE_INSTRUCTOR' },
  }

];