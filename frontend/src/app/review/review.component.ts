import { Component, OnInit} from '@angular/core';
import { Review } from '../models/Review';
import { ReviewService } from './review.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({ 
    templateUrl: 'review.component.html',
    styleUrls: ['./review.component.css']
})
export class ReviewComponent implements OnInit{
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


}