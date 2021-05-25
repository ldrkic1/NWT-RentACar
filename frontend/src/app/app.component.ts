
import { QuestionNotification } from './models/QuestionNotification';
import { QuestionNotificationService } from './QuestionNotification.service';
import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'frontend';

  public questionNotifications: QuestionNotification[]=[];

  constructor(private questionNotificationService: QuestionNotificationService){}
  
  ngOnInit() {
      this.getQuestionNotifications();
      
      
    }


  public getQuestionNotifications(): void {
      this.questionNotificationService.getQuestionNotifications().subscribe(
        (response: QuestionNotification[]) => {
          this.questionNotifications = response;
          console.log(this.questionNotifications);
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
    }
}
