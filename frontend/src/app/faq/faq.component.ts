import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Answer } from '../models/Answer';
import { Question } from '../models/Question';
import { QuestionService } from './faq.service';

@Component({ templateUrl: 
    'faq.component.html',
    styleUrls: ['./faq.component.css']
})
export class FaqComponent implements OnInit {
    public answersAndQuestions: Answer[] = [];

    constructor(private questionService: QuestionService) {}


    ngOnInit() {
        this.getAnswersAndQuestions();
    }

    public getAnswersAndQuestions(): void {
        this.questionService.getAnswersAndQuestions().subscribe(
            (response: Answer[]) => {
                this.answersAndQuestions = response;
                console.log(response);
            },
            (error: HttpErrorResponse) => {
              alert(error.message);
            }
        );
    }

}