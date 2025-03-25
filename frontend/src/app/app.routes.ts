import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { authGuard } from './guard/auth.guard';
import { SessionsComponent } from './components/home/sessions/sessions.component';
import { DashboardComponent } from './components/home/dashboard/dashboard.component';
import { HomeworksComponent } from './components/home/homeworks/homeworks.component';

export const routes: Routes = [
    {   path: 'login',
        component: LoginComponent,
    },
    {   path: '',
        component: HomeComponent,
        canActivate: [authGuard],

        children: [
            {
                path: '',
                component: DashboardComponent,
            },
            {
                path: 'sessions',
                component: SessionsComponent,
            },
            {
                path: 'homeworks',
                component: HomeworksComponent,
            }
        ],
    },
];
