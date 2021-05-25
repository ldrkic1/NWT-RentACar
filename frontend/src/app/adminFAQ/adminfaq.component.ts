import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { QuestionService } from '../faq/faq.service';
import { Answer } from '../models/Answer';
import { Question } from '../models/Question';

@Component({ templateUrl: 
    'adminfaq.component.html',
    styleUrls: ['./adminfaq.component.css']
})
export class AdminFaqComponent implements OnInit {
    public questions: Question[] = [];
    public questionObj!: Question;
    public answerOnQuestion!: Answer;
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
      public onDeleteAnswer(answerId: number | undefined, addAnswerForm: NgForm): void {
        const btn = document.getElementById('deleteBtn');
        if(answerId !== undefined) this.questionService.deleteAnswer(answerId).subscribe(
          (response: any) => {
            console.log(response);
            this.questionObj.answered= !this.questionObj.answered;
            this.getQuestions();
            addAnswerForm.reset();
            if(btn !== null) btn.click();

          },
          (error: HttpErrorResponse) => {
            addAnswerForm.reset();
            this.getQuestions();
            if(btn !== null) btn.click();
          }
        );
      }
      public onOpenModal(question: Question): void {
        const container = document.getElementById('main-container');
        const button = document.createElement('button');
        button.type = 'button';
        button.style.display = 'none';
        button.setAttribute('data-toggle', 'modal');
        button.setAttribute('data-target', '#questionAnswerModal');
        if(container!== null) container.appendChild(button);
        this.questionObj = question;
        if(question.answered) {
          this.questionService.getAnwserOnQuestion(question.id).subscribe(
            (response: Answer) => {
              this.answerOnQuestion=response;
              this.answerOnQuestion.answer = response.answer;
            },
            (error: HttpErrorResponse) => {
              console.log(error);
            }
          );
        }
        button.click();
      }

  public onAnswerQuestion(addAnswerForm: NgForm): void {
    addAnswerForm.value.user={};
    addAnswerForm.value.user.username="";
    this.questionService.addAnswerOnQuestion(addAnswerForm.value,this.questionObj.id,addAnswerForm.controls['username'].value).subscribe(
      (response: Answer) => {
        console.log(response);
        this.questionObj.answered= !this.questionObj.answered;
        this.answerOnQuestion=response;
      },
      (error: HttpErrorResponse) => {
        addAnswerForm.reset();
      }
    );
  }
}