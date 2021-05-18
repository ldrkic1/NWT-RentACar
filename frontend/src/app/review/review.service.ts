import { Injectable } from '@angular/core';
import { Review } from '../models/Review';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})

export class ReviewService{
    private apiServerUrl=environment.apiBaseUrl;

    constructor(private http: HttpClient){}

    public getReviews(): Observable<Review[]> {
        return this.http.get<Review[]>(`${this.apiServerUrl}/clientcares/review/all`);
      }
}
