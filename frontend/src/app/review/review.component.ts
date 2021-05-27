import { Component, OnInit} from '@angular/core';
import { Review } from '../models/Review';
import { ReviewService } from './review.service';
import { HttpErrorResponse } from '@angular/common/http';
import { NgForm } from '@angular/forms';
import { HomeService } from '../home/home.service';



@Component({ 
    templateUrl: 'review.component.html',
    styleUrls: ['./review.component.css']
})
export class ReviewComponent implements OnInit{ 
  

    public reviews: Review[]=[];
    newTitleArr:any[]=[];
    constructor(private reviewService: ReviewService, public homeService: HomeService){}
    
    ngOnInit() {
        this.getReviews();
        
      }


      splitArr(arr:any, size:number) {
        let newArr = [];
        for(let i = 0; i< arr.length; i += size) {
          newArr.push(arr.slice(i, i+size));
        }
        return newArr;
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

      public onAddReview(addReviewForm: NgForm): void {
        //alert(addReviewForm.controls['username'].value);
        console.log(addReviewForm.value);
        addReviewForm.value.user = {};
        addReviewForm.value.user.username = "";
        let user = sessionStorage.getItem("username");
        addReviewForm.value.user.username=user;
        this.reviewService.addReview(addReviewForm.value, addReviewForm.value.user.username).subscribe(
          (response: Review) => {
            console.log(response);
            this.getReviews();
            addReviewForm.reset();
          },
          (error: HttpErrorResponse) => {
            alert(error.message);
            addReviewForm.reset();
          }
        );
      }
    
}