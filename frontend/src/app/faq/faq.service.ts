import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Answer } from '../models/Answer';
import { Question } from '../models/Question';
import { User } from '../models/User';
import { Role } from '../models/Role';


@Injectable({providedIn: 'root'})
export class QuestionService {
    private apiBaseUrl = environment.apiBaseUrl;
    private user: User = {
        id: 0,
        firstName:'',
        lastName:'',
        username: '',
        roles: new Set<Role>()
    };

    private temp = {
        username:''
    };
    constructor(private http: HttpClient) {}

    public getAnswersAndQuestions(): Observable<Answer[]> {
        return this.http.get<Answer[]>(`${this.apiBaseUrl}/clientcares/answer/all`)
    }


    public addQuestion(question: Question, u: string): Observable<Question> {
        question.user.username = u;
        return this.http.post<Question>(`${this.apiBaseUrl}/clientcares/question/newQuestion`, question);
    }

    public getAllQuestions(): Observable<Question[]> {
        return this.http.get<Question[]>(`${this.apiBaseUrl}/clientcares/question/all`);
    }

    public deleteQuestion(id: number): Observable<any> {
        return this.http.delete<any>(`${this.apiBaseUrl}/clientcares/question?id=${id}`);
    }

}
