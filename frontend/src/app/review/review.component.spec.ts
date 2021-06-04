import { HttpClientTestingModule } from "@angular/common/http/testing";
import { ComponentFixture, TestBed } from "@angular/core/testing";
import { RouterTestingModule } from "@angular/router/testing";
import { ReviewComponent } from ".";
import { DebugElement } from '@angular/core';
import { BrowserModule, By } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { Observable, of } from "rxjs";
import { ReviewService } from "./review.service";
import { Review } from "../models/Review";
import { Role } from "../models/Role";
describe('ReviewComponent', () => {
    let component: ReviewComponent;
    let fixture: ComponentFixture<ReviewComponent>;
    let debugElement: DebugElement;
    let htmlElement : HTMLElement;

    beforeEach(async () => {
        TestBed.configureTestingModule({
          imports: [ HttpClientTestingModule, RouterTestingModule, FormsModule, ReactiveFormsModule, BrowserModule ],
          declarations: [
            ReviewComponent
          ],
          providers: [
            {provide: ReviewService, useClass: ReviewServiceStub}
        ]
        });
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(ReviewComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();

    });

    it('should create', () => {
      expect(component).toBeTruthy();
    }); 
    
    it('should contain h4 tag', () => {
        debugElement = fixture.debugElement.query(By.css('h4'));
        htmlElement = debugElement.nativeElement;
        expect(htmlElement.textContent).toEqual('Not Yet Convinced?');
    });

    it('should contain h1 tag', () => {
        debugElement = fixture.debugElement.query(By.css('h1'));
        htmlElement = debugElement.nativeElement;
        expect(htmlElement.textContent).toEqual('Read Customer Reviews');
    });

    it('should show 3 reviews', () => {
        let temp: ReviewServiceStub;
        temp = new ReviewServiceStub();
        temp.getReviews();
        component.reviews = temp.reviews;
        fixture.detectChanges();
        const debugElements = fixture.debugElement.queryAll(By.css('p'));
        expect(debugElements.length - 1).toEqual(3);
    });

    it('should first review.title be Recenzija 1', () => {
        let temp: ReviewServiceStub;
        temp = new ReviewServiceStub();
        temp.getReviews();
        component.reviews = temp.reviews;
        fixture.detectChanges();
        const debugElements = fixture.debugElement.queryAll(By.css('p'));
        const htmlEl: HTMLParagraphElement = debugElements[0].nativeElement;
        expect(htmlEl.textContent).toEqual('Sadrzaj 1. recenzije...');
    });
})
class ReviewServiceStub{
    reviews: Review[] = [];
    getReviews(): Observable<Review[]> {
        for(let i= 0; i<3; i++) {
            this.reviews.push({
                id: i+1,
                title: 'Recenzija ' +  (i+1).toString(),
                review: 'Sadrzaj '+ (i+1).toString() + '. recenzije...',
                user: {
                    id: 1,
                    firstName: 'Test',
                    lastName: 'Test',
                    username: 'testic',
                    email:'test@gmail.com',
                    password: 'Test123@',
                    roles: new Set<Role>()
                }
            })
        }
        return of(this.reviews);
    }
}