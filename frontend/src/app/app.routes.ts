import { Routes } from '@angular/router';
import { Login } from './login/login';
import { Register } from './register/register';
import { Dashboard } from './client/dashboard/dashboard';
import { authGuard } from './guards/auth-guard';
import { ClassList } from './client/class-list/class-list';
import { MyBookings } from './client/my-bookings/my-bookings';
import { Landing } from './landing/landing';

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
    path: 'dashboard',
    component: Dashboard,
  },
  {
    path: 'browse-classes',
    component: ClassList,
    canActivate: [authGuard],
  },
  {
    path: 'my-bookings',
    component: MyBookings,
    canActivate: [authGuard],
  },
];
