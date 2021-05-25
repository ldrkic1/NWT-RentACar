import { Injectable } from '@angular/core';
import { Review } from './models/Review';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

import { User } from './models/User';
import { QuestionNotification } from './models/QuestionNotification';
@Injectable({
    providedIn: 'root'
})

export class QuestionNotificationService{
    private apiServerUrl=environment.apiBaseUrl;

    constructor(private http: HttpClient){}

    public getQuestionNotifications(): Observable<QuestionNotification[]> {
        return this.http.get<QuestionNotification[]>(`${this.apiServerUrl}/notifications/questionNotifications/all`);
      }
 
}
