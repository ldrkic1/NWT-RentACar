import { Component, OnInit} from '@angular/core';
import { Review } from '../models/Review';
import { AdminReviewService } from './adminReview.service';
import { HttpErrorResponse } from '@angular/common/http';
import { NgForm } from '@angular/forms';
import { ReviewService } from '../review/review.service';



@Component({ 
    templateUrl: 'adminReview.component.html',
    styleUrls: ['./adminReview.component.css']
})
export class AdminReviewComponent implements OnInit{ 
    
    public reviews: Review[]=[];
  
    constructor(private reviewService: ReviewService){}
    
    ngOnInit() {
        this.getReviews();
      }


    public getReviews(): void {
        this.reviewService.getReviews().subscribe(
          (response: Review[]) => {
            this.reviews = response;
            console.log(this.reviews);
          },
          (error: HttpErrorResponse) => {
            alert(error.message);
          }
        );
      }

      public onDeleteReview(reviewId: number): any {
        this.reviewService.deleteReview(reviewId).subscribe(
          (response: any) => {
            console.log(response);
            this.getReviews();
        
          },
          (error: HttpErrorResponse) => {
         
            this.getReviews();
          }
        );
      }

}
  