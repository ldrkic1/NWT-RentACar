import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from './home';
import { VehicleComponent } from './vehicle';
import { ReviewComponent } from './review';
import { FaqComponent } from './faq';
import { SignUpComponent } from './signup';

const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'vehicle', component: VehicleComponent },
    { path: 'review', component: ReviewComponent },
    { path: 'faq', component: FaqComponent },
    { path: 'signup', component: SignUpComponent},

    // otherwise redirect to home
    { path: '**', redirectTo: '' }
];

export const appRoutingModule = RouterModule.forRoot(routes);