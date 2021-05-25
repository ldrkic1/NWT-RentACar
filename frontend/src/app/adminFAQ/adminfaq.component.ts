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

    public searchClients(key: string): void {
      console.log(key);
      const results: Question[] = [];
      for (const question of this.questions) {
        if (question.user.firstName.toLowerCase().indexOf(key.toLowerCase()) !== -1
        || question.user.lastName.toLowerCase().indexOf(key.toLowerCase()) !== -1
        || question.user.username.toLowerCase().indexOf(key.toLowerCase()) !== -1
        || question.title.toLowerCase().indexOf(key.toLowerCase()) !== -1
        ) {
          results.push(question);
        }
      }
      this.questions = results;
      if (results.length === 0 || !key) {
        this.getQuestions();
      }
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