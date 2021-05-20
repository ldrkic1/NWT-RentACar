import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { QuestionService } from '../faq/faq.service';
import { Question } from '../models/Question';

@Component({ templateUrl: 
    'adminfaq.component.html',
    styleUrls: ['./adminfaq.component.css']
})
export class AdminFaqComponent implements OnInit {
    public questions: Question[] = [];
    constructor(private questionService: QuestionService) {}


    ngOnInit() {
        this.getQuestions();
    }

    public getQuestions(): void {
        this.questionService.getAllQuestions().subscribe(
            (response: Question[]) => {
                this.questions = response;
                console.log(response);
            },
            (error: HttpErrorResponse) => {
              alert(error.message);
            }
        );
    }

    public onDeleteQuestion(questionId: number): void {
        this.questionService.deleteQuestion(questionId).subscribe(
          (response: any) => {
            console.log(response);
            this.getQuestions();
          },
          (error: HttpErrorResponse) => {
            this.getQuestions();
          }
        );
      }
}