import { Routes } from '@angular/router';
import { Login } from './shared/components/login/login';
import { Register } from './shared/components/register/register';
import { Dashboard } from './shared/components/dashboard/dashboard';
import { authGuard } from './core/guards/auth-guard';
import { ClassList } from './features/client/class-list/class-list';
import { MyBookings } from './features/client/my-bookings/my-bookings';
import { Landing } from './features/landing/landing';
import { Schedule } from './features/trainer/schedule/schedule';
import { Unauthorized } from './shared/components/unauthorized/unauthorized';
import { FitnessClassStepper } from './features/trainer/fitness-class-stepper/fitness-class-stepper';
import { Trainers } from './features/admin/trainers/trainers';
import { Clients } from './features/admin/clients/clients';
import { Classes } from './features/admin/classes/classes';

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
    component: Dashboard
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
  {
    path: 'admin/clients',
    component: Clients,
    canActivate: [authGuard],
    data: { expectedRole: 'ROLE_ADMIN' },
  },
  {
    path: 'admin/classes',
    component: Classes,
    canActivate: [authGuard],
    data: { expectedRole: 'ROLE_ADMIN' },
  },
];
