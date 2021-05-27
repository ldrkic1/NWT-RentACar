import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
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

    public onAddQuestion(addQuestionForm: NgForm): void {
      addQuestionForm.value.user={};
      addQuestionForm.value.user.username="";
      let user = sessionStorage.getItem("username");
      addQuestionForm.value.user.username=user;
      this.questionService.addQuestion(addQuestionForm.value, addQuestionForm.value.user.username).subscribe(
        (response: Question) => {
          console.log(response);
          this.getAnswersAndQuestions();
          addQuestionForm.reset();
          
        },
        (error: HttpErrorResponse) => {
          addQuestionForm.reset();
        }
      );
    }
}