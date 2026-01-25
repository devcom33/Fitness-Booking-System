import { Routes } from '@angular/router';
import { Login } from './login/login';
import { Register } from './register/register';
import { Dashboard } from './dashboard/dashboard';
import { authGuard } from './guards/auth-guard';
import { ClassList } from './class-list/class-list';
import { MyBookings } from './my-bookings/my-bookings';

export const routes: Routes = [
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
