import { HttpClientTestingModule } from "@angular/common/http/testing";
import { ComponentFixture, TestBed } from "@angular/core/testing";
import { RouterTestingModule } from "@angular/router/testing";
import { DebugElement } from '@angular/core';
import { BrowserModule, By } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { Observable, of } from "rxjs";
import { Role } from "../models/Role";
import { Review } from "../models/Review";
import { ReviewService } from "../review/review.service";
import { AdminReviewComponent } from ".";
describe('AdminReviewComponent', () => {
    let component: AdminReviewComponent;
    let fixture: ComponentFixture<AdminReviewComponent>;
    let debugElement: DebugElement;
    let htmlElement : HTMLElement;

    beforeEach(async () => {
        TestBed.configureTestingModule({
          imports: [ HttpClientTestingModule, RouterTestingModule, FormsModule, ReactiveFormsModule, BrowserModule ],
          declarations: [
            AdminReviewComponent
          ],
          providers: [
              {provide: ReviewService, useClass: ReviewServiceStub}
          ]
        });
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(AdminReviewComponent);
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
        expect(htmlElement.textContent).toEqual('Review Details');
    });

    it('should show 3 reviews', () => {
        let temp: ReviewServiceStub;
        temp = new ReviewServiceStub();
        temp.getReviews();
        component.reviews = temp.reviews;
        fixture.detectChanges();
        const debugElements = fixture.debugElement.queryAll(By.css('table'));
        expect(debugElements.length).toEqual(1);
    });
    it('shouldfirst review.title be Recenzija 1', () => {
        let temp: ReviewServiceStub;
        temp = new ReviewServiceStub();
        temp.getReviews();
        component.reviews = temp.reviews;
        fixture.detectChanges();
        const element = fixture.debugElement.queryAll(By.css('td'));
        const htmlEl: HTMLTableDataCellElement = element[3].nativeElement;
        expect(htmlEl.textContent).toEqual('Recenzija 1');
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