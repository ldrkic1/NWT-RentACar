import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Answer } from '../models/Answer';

@Injectable({providedIn: 'root'})
export class QuestionService {
    private apiBaseUrl = environment.apiBaseUrl;
    constructor(private http: HttpClient) {}

    public getAnswersAndQuestions(): Observable<Answer[]> {
        return this.http.get<Answer[]>(`${this.apiBaseUrl}/clientcares/answer/all`)
    }

}
