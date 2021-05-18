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

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    VehicleComponent,
    ReviewComponent,
    FaqComponent
  ],
  imports: [
    BrowserModule,
    appRoutingModule,
    HttpClientModule
  ],
  providers: [ReviewService,QuestionService],
  bootstrap: [AppComponent]
})
export class AppModule { }
