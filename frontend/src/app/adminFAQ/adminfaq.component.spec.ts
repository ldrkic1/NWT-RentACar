import { HttpClientTestingModule } from "@angular/common/http/testing";
import { ComponentFixture, TestBed } from "@angular/core/testing";
import { RouterTestingModule } from "@angular/router/testing";
import { DebugElement } from '@angular/core';
import { BrowserModule, By } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { Answer } from "../models/Answer";
import { Observable, of } from "rxjs";
import { Role } from "../models/Role";
import { AdminFaqComponent } from ".";
import { QuestionService } from "../faq/faq.service";
import { Question } from "../models/Question";
describe('AdminFaqComponent', () => {
    let component: AdminFaqComponent;
    let fixture: ComponentFixture<AdminFaqComponent>;
    let debugElement: DebugElement;
    let htmlElement : HTMLElement;

    beforeEach(async () => {
        TestBed.configureTestingModule({
          imports: [ HttpClientTestingModule, RouterTestingModule, FormsModule, ReactiveFormsModule, BrowserModule ],
          declarations: [
            AdminFaqComponent
          ],
          providers: [
              {provide: QuestionService, useClass: QuestionServiceStub}
          ]
        });
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(AdminFaqComponent);
        component = fixture.componentInstance;
        component.ngOnInit();
        fixture.detectChanges();

    });

    it('should create', () => {
      expect(component).toBeTruthy();
    }); 
    
    it('should contain h4 tag', () => {
        debugElement = fixture.debugElement.query(By.css('h4'));
        htmlElement = debugElement.nativeElement;
        expect(htmlElement.textContent).toEqual('Question Details');
    });

    it('should show 3 questions', () => {
        let temp: QuestionServiceStub;
        temp = new QuestionServiceStub();
        temp.getAllQuestions();
        component.questions = temp.questions;
        fixture.detectChanges();
        const debugElements = fixture.debugElement.queryAll(By.css('table'));
        expect(debugElements.length).toEqual(1);
    });
    it('should first question.title be Naslov 1', () => {
        let temp: QuestionServiceStub;
        temp = new QuestionServiceStub();
        temp.getAllQuestions();
        component.questions = temp.questions;
        fixture.detectChanges();
        const element = fixture.debugElement.queryAll(By.css('td'));
        const htmlEl: HTMLTableDataCellElement = element[3].nativeElement;
        expect(htmlEl.textContent).toEqual('Naslov 1');
    });
})

class QuestionServiceStub{
    questions: Question[] = [];
    getAllQuestions(): Observable<Question[]> {
        for(let i= 0; i<3; i++) {
            this.questions.push({
                id: i+1,
                title:'Naslov '+ (i+1).toString(),
                question: 'Pitanje '+ (i+1).toString(),
                answered: true,
                user: {
                    id: 2,
                    firstName: 'Test',
                    lastName: 'Test',
                    username: 'testic',
                    email:'test@gmail.com',
                    password: 'Test123@',
                    roles: new Set<Role>()
                }
            })
        }
        return of(this.questions);
    }
}