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
import { HttpClientModule } from '@angular/common/http';
import { QuestionService } from './faq/faq.service';
import { FormsModule } from '@angular/forms';
import { AdminReviewComponent } from './AdminReview';
import { AdminFaqComponent } from './adminFAQ';
import { ClientComponent } from './client';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    VehicleComponent,
    ReviewComponent,
    FaqComponent,
    AdminReviewComponent,
    AdminFaqComponent,
    ClientComponent
  ],
  imports: [
    BrowserModule,
    appRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [ReviewService,QuestionService],
  bootstrap: [AppComponent]
})
export class AppModule { }
