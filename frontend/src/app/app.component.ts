
import { QuestionNotification } from './models/QuestionNotification';
import { QuestionNotificationService } from './QuestionNotification.service';
import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { HomeService } from './home/home.service';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'frontend';

  public questionNotifications: QuestionNotification[]=[];

  constructor(private questionNotificationService: QuestionNotificationService, public homeService: HomeService){}
  
  ngOnInit() {
      this.getQuestionNotifications();
      setTimeout(()=>{this.ngOnInit()}, 1000*10)
      
    }


  public getQuestionNotifications(): void {
      this.questionNotificationService.getQuestionNotifications().subscribe(
        (response: QuestionNotification[]) => {
          this.questionNotifications = response;
          console.log(this.questionNotifications);
          //this.getQuestionNotifications();
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
    }
}
