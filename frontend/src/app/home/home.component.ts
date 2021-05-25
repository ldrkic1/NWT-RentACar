import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';

@Component({ 
    templateUrl: 'home.component.html',
    styleUrls: ['./home.component.css'] })
export class HomeComponent {


    public onRegistration(registrationForm: NgForm): void {
        //alert(addReviewForm.controls['username'].value);
        console.log(registrationForm.value);
        /*addReviewForm.value.user = {};
        addReviewForm.value.user.username = "";
        this.reviewService.addReview(addReviewForm.value, addReviewForm.controls['username'].value).subscribe(
          (response: Review) => {
            console.log(response);
            this.getReviews();
            addReviewForm.reset();
          },
          (error: HttpErrorResponse) => {
            alert(error.message);
            registrationForm.reset();
          }
        );*/
      }
}