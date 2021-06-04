import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { appRoutingModule } from './app.routing';
//import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home';
import { VehicleComponent } from './vehicle';
import { ReviewComponent } from './review';
import { FaqComponent } from './faq';
import { ReviewService } from './review/review.service';
import { QuestionService } from './faq/faq.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AdminReviewComponent } from './adminReview';
import { AdminFaqComponent } from './adminFAQ';
import { ClientComponent } from './client';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { LogoutComponent } from './logout/logout.component';
import { SignUpComponent } from './signup';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    VehicleComponent,
    ReviewComponent,
    FaqComponent,
    AdminReviewComponent,
    AdminFaqComponent,
    ClientComponent,
    LogoutComponent,
    SignUpComponent
  ],
  imports: [
    BrowserModule,
    appRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [ReviewService,QuestionService],
  bootstrap: [AppComponent]
})
export class AppModule { }
