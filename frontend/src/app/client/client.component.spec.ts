import { HttpClientTestingModule } from "@angular/common/http/testing";
import { ComponentFixture, TestBed } from "@angular/core/testing";
import { RouterTestingModule } from "@angular/router/testing";
import { DebugElement } from '@angular/core';
import { BrowserModule, By } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { Observable, of } from "rxjs";
import { Role } from "../models/Role";
import { User } from "../models/User";
import { ClientService } from "./client.service";
import { ClientComponent } from ".";
describe('ClientComponent', () => {
    let component: ClientComponent;
    let fixture: ComponentFixture<ClientComponent>;
    let debugElement: DebugElement;
    let htmlElement : HTMLElement;

    beforeEach(async () => {
        TestBed.configureTestingModule({
          imports: [ HttpClientTestingModule, RouterTestingModule, FormsModule, ReactiveFormsModule, BrowserModule ],
          declarations: [
            ClientComponent
          ],
          providers: [
              {provide: ClientService, useClass: ClienterviceStub}
          ]
        });
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(ClientComponent);
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
        expect(htmlElement.textContent).toEqual('Client Details');
    });

    it('should show 3 clients', () => {
        let temp: ClienterviceStub;
        temp = new ClienterviceStub();
        temp.getClients();
        component.clients = temp.clients;
        fixture.detectChanges();
        const debugElements = fixture.debugElement.queryAll(By.css('table'));
        expect(debugElements.length).toEqual(1);
    });
    it('should second client.username be testic2', () => {
        let temp: ClienterviceStub;
        temp = new ClienterviceStub();
        temp.getClients();
        component.clients = temp.clients;
        fixture.detectChanges();
        const element = fixture.debugElement.queryAll(By.css('td'));
        const htmlEl: HTMLTableDataCellElement = element[8].nativeElement;
        expect(htmlEl.textContent).toEqual('testic2');
    });
})

class ClienterviceStub{
    clients: User[] = [];
    getClients(): Observable<User[]> {
        for(let i= 0; i<3; i++) {
            this.clients.push({
                id: i+1,
                firstName: 'Test',
                lastName: 'Test',
                username: 'testic'+(i+1).toString(),
                email:'test'+(i+1).toString() +'@gmail.com',
                password: 'Test123@',
                roles: new Set<Role>()
            })
        }
        return of(this.clients);
    }
}