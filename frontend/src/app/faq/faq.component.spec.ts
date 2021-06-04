import { HttpClientTestingModule } from "@angular/common/http/testing";
import { ComponentFixture, TestBed } from "@angular/core/testing";
import { RouterTestingModule } from "@angular/router/testing";
import { FaqComponent } from ".";
import { DebugElement } from '@angular/core';
import { BrowserModule, By } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { QuestionService } from "./faq.service";
import { Answer } from "../models/Answer";
import { Observable, of } from "rxjs";
import { Role } from "../models/Role";
describe('FaqComponent', () => {
    let component: FaqComponent;
    let fixture: ComponentFixture<FaqComponent>;
    let debugElement: DebugElement;
    let htmlElement : HTMLElement;

    beforeEach(async () => {
        TestBed.configureTestingModule({
          imports: [ HttpClientTestingModule, RouterTestingModule, FormsModule, ReactiveFormsModule, BrowserModule ],
          declarations: [
            FaqComponent
          ],
          providers: [
              {provide: QuestionService, useClass: QuestionServiceStub}
          ]
        });
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(FaqComponent);
        component = fixture.componentInstance;
        component.ngOnInit();
        fixture.detectChanges();

    });

    it('should create', () => {
      expect(component).toBeTruthy();
    }); 
    
    it('should contain h6 tag', () => {
        debugElement = fixture.debugElement.query(By.css('h6'));
        htmlElement = debugElement.nativeElement;
        expect(htmlElement.textContent).toEqual('FAQ');
    });

    it('should show 3 questions', () => {
        let temp: QuestionServiceStub;
        temp = new QuestionServiceStub();
        temp.getAnswersAndQuestions();
        component.answersAndQuestions = temp.answers;
        fixture.detectChanges();
        const debugElements = fixture.debugElement.queryAll(By.css('p'));
        expect(debugElements.length).toEqual(3);
    });

    it('should first question.question be Pitanje 1', () => {
        let temp: QuestionServiceStub;
        temp = new QuestionServiceStub();
        temp.getAnswersAndQuestions();
        component.answersAndQuestions = temp.answers;
        fixture.detectChanges();
        const debugElements = fixture.debugElement.queryAll(By.css('strong'));
        const htmlEl: HTMLParagraphElement = debugElements[0].nativeElement;
        expect(htmlEl.textContent?.split('\n',1).toString()).toEqual('Pitanje 1');
    });
})



class QuestionServiceStub{
    answers: Answer[] = [];
    getAnswersAndQuestions(): Observable<Answer[]> {
        for(let i= 0; i<3; i++) {
            this.answers.push({
                id: i+1,
                answer: 'odgovor',
                user: {
                    id: 1,
                    firstName: 'Test',
                    lastName: 'Test',
                    username: 'testic',
                    email:'test@gmail.com',
                    password: 'Test123@',
                    roles: new Set<Role>()
                },
                question: {
                    id: i-1,
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
                }
            })
        }
        return of(this.answers);
    }
}