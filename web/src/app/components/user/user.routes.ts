import { Route } from '@angular/router';
import { AuthGuard } from '@core/guard/auth.guard';
import { UserComponent } from './user.component';

export const USER_ROUTE: Route[] = [
  {
    path: '',
    component: UserComponent,
    canActivate: [AuthGuard]
  }
];
