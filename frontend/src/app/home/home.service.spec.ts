import { ComponentFixture, TestBed } from "@angular/core/testing";
import { HomeService } from './home.service';
import {HttpClientModule, HttpErrorResponse} from '@angular/common/http';

describe('HomeService',() => {

    let service: HomeService;
    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientModule],
            providers: [HomeService]
        });
        service = TestBed.get(HomeService);
    });

  it('should be created', () => {
    const service: HomeService = TestBed.get(HomeService);
    expect(service).toBeTruthy();
  });

  it('should return ROLE_CLIENT', () => {
    let role = '';
    service.authenticate('ldrkic1','1PassworD1*').subscribe(
        (response: Response) => {
            console.log(response);
            const obj = JSON.parse(JSON.stringify(response));
            role = obj.role[0].roleName;
            expect(role).toEqual('ROLE_CLIENT'); 
        }
    );

  });
 it('should return error message', () => {
    service.authenticate('ldrkiccc','1PassworD1*').subscribe(
        (response: Response) => {
            console.log(response);
            const obj = JSON.parse(JSON.stringify(response));
        },
        (error: HttpErrorResponse) => {
            expect(error.error.message).toEqual('Incorrect username or password'); 
        }
    );
  });
});