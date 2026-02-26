import { Routes } from '@angular/router';
import { Login } from './shared/login/login';
import { Register } from './shared/register/register';
import { Dashboard } from './shared/dashboard/dashboard';
import { authGuard } from './core/guards/auth-guard';
import { ClassList } from './client/class-list/class-list';
import { MyBookings } from './client/my-bookings/my-bookings';
import { Landing } from './landing/landing';
import { Schedule } from './trainer/schedule/schedule';
import { Unauthorized } from './shared/unauthorized/unauthorized';
import { FitnessClassStepper } from './trainer/fitness-class-stepper/fitness-class-stepper';
import { Trainers } from './admin/trainers/trainers';

export const routes: Routes = [
  {
    path: '',
    component: Landing,
  },
  {
    path: 'login',
    component: Login,
  },
  {
    path: 'register',
    component: Register,
  },
  {
    path: 'unauthorized',
    component: Unauthorized,
  },
  {
    path: 'dashboard',
    component: Dashboard,
  },
  {
    path: 'browse-classes',
    component: ClassList,
    canActivate: [authGuard],
    data: { expectedRole: 'ROLE_CLIENT' }
  },
  {
    path: 'my-bookings',
    component: MyBookings,
    canActivate: [authGuard],
    data: { expectedRole: 'ROLE_CLIENT' }
  },
  {
    path: 'trainer/schedule',
    component: Schedule,
    canActivate: [authGuard],
    data: { expectedRole: 'ROLE_INSTRUCTOR' },
  },
  {
    path: 'trainer/create-class',
    component: FitnessClassStepper,
    canActivate: [authGuard],
    data: { expectedRole: 'ROLE_INSTRUCTOR' },
  },
  {
    path: 'admin/trainers',
    component: Trainers,
    canActivate: [authGuard],
    data: { expectedRole: 'ROLE_ADMIN' },
  },
];
