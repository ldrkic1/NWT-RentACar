import { Component, OnInit} from '@angular/core';
import { Review } from '../models/Review';
import { AdminReviewService } from './adminReview.service';
import { HttpErrorResponse } from '@angular/common/http';
import { NgForm } from '@angular/forms';
import { ReviewService } from '../review/review.service';
import { User } from '../models/User';

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

      public searchClients(key: string): void {
        console.log(key);
        const results: Review[] = [];
        for (const review of this.reviews) {
          if (review.user.firstName.toLowerCase().indexOf(key.toLowerCase()) !== -1
          || review.user.lastName.toLowerCase().indexOf(key.toLowerCase()) !== -1
          || review.user.username.toLowerCase().indexOf(key.toLowerCase()) !== -1
          || review.title.toLowerCase().indexOf(key.toLowerCase()) !== -1
          ) {
            results.push(review);
          }
        }
        this.reviews = results;
        if (results.length === 0 || !key) {
          this.getReviews();
        }
      }

}
  