import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from './home';
import { VehicleComponent } from './vehicle';
import { ReviewComponent } from './review';
import { FaqComponent } from './faq';
import { SignUpComponent } from './signup';
import { AdminReviewComponent } from './adminReview';
import { AdminFaqComponent } from './adminFAQ';
import { ClientComponent } from './client';
import { LogoutComponent } from './logout/logout.component';
import {CarShareComponent} from "./CarShare";
import {ReservationComponent} from "./Reservation";

const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'vehicle', component: VehicleComponent },
    { path: 'review', component: ReviewComponent },
    { path: 'faq', component: FaqComponent },
    { path: 'signup', component: SignUpComponent},
    { path: 'adminReview', component: AdminReviewComponent},
    { path: 'adminFAQ', component: AdminFaqComponent},
    { path: 'client', component: ClientComponent},
    { path: 'logout', component: LogoutComponent},
    { path: 'carShare', component: CarShareComponent},
    { path: 'reservations', component: ReservationComponent},
    // otherwise redirect to home
    { path: '**', redirectTo: '' }
];

export const appRoutingModule = RouterModule.forRoot(routes);
