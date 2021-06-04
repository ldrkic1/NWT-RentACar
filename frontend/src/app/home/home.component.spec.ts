import { HttpClientTestingModule } from "@angular/common/http/testing";
import { ComponentFixture, TestBed } from "@angular/core/testing";
import { RouterTestingModule } from "@angular/router/testing";
import { HomeComponent } from ".";
import { DebugElement } from '@angular/core';
import { By } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { HomeService } from "./home.service";
describe('HomeComponent', () => {
    let component: HomeComponent;
    let fixture: ComponentFixture<HomeComponent>;
    let debugElement: DebugElement;
    let htmlElement : HTMLElement;

    beforeEach(async () => {
        TestBed.configureTestingModule({
          imports: [ HttpClientTestingModule, RouterTestingModule, FormsModule, ReactiveFormsModule ],
          declarations: [
            HomeComponent
          ],
          providers: [
            {provide: HomeService, useClass: HomeServiceStub}
        ]
        });
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(HomeComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
        debugElement = fixture.debugElement.query(By.css('p'));
        htmlElement = debugElement.nativeElement;
    });

    it('should create', () => {
      expect(component).toBeTruthy();
    }); 
    
    it('should display login message', () => {
        expect(htmlElement.textContent).toEqual('Sign In to your account');
    });

    it('should display login button', () => {
      debugElement = fixture.debugElement.query(By.css('button'));
      htmlElement = debugElement.nativeElement;
      expect(htmlElement.textContent).toEqual('Login');
    });
})

class HomeServiceStub{
}