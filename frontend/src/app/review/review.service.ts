import { Injectable } from '@angular/core';
import { Review } from '../models/Review';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

import { User } from '../models/User';
@Injectable({
    providedIn: 'root'
})

export class ReviewService{
    private apiServerUrl=environment.apiBaseUrl;

    constructor(private http: HttpClient){}

    public getReviews(): Observable<Review[]> {
        return this.http.get<Review[]>(`${this.apiServerUrl}/clientcares/review/all`);
      }
      public addReview(review: Review, username: string): Observable<Review> {
        console.log("1:");
        console.log(review);
        console.log(username);
        review.user.username=username;
        console.log("2:");
        console.log(review);
        return this.http.post<Review>(`${this.apiServerUrl}/clientcares/review/newReview`, review);
      }
}
