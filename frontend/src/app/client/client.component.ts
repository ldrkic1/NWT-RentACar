import { Component, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import { User } from '../models/User';
import { ClientService } from './client.service';
import { HttpErrorResponse } from '@angular/common/http';
import { NgForm } from '@angular/forms';



@Component({ 
    templateUrl: 'client.component.html',
    styleUrls: ['./client.component.css']
})
export class ClientComponent implements OnInit { 
    public clients: User[]=[];
    public message: string = '';
  
    constructor(private clientService: ClientService){}
    
    ngOnInit() {
        this.getClients();
      }


    public getClients(): void {
        this.clientService.getClients().subscribe(
          (response: User[]) => {
            this.clients = response;
            console.log(this.clients);
          },
          (error: HttpErrorResponse) => {
            alert(error.message);
          }
        );
      }

      public searchClients(key: string): void {
        console.log(key);
        const results: User[] = [];
        for (const client of this.clients) {
          if (client.firstName.toLowerCase().indexOf(key.toLowerCase()) !== -1
          || client.lastName.toLowerCase().indexOf(key.toLowerCase()) !== -1
          || client.email.toLowerCase().indexOf(key.toLowerCase()) !== -1
          || client.username.toLowerCase().indexOf(key.toLowerCase()) !== -1
          ) {
            results.push(client);
          }
        }
        this.clients = results;
        if (results.length === 0 || !key) {
          this.getClients();
        }
      }

      
      public onRegistration(registrationForm: NgForm): void {
        //alert(addReviewForm.controls['username'].value);
        console.log(registrationForm.value);
        if(registrationForm.controls['password'].value !== registrationForm.controls['passwordRepeat'].value) {
          this.message = 'Passwords do not match!';
        }
        else {
          this.clientService.registration(registrationForm.value).subscribe(
            (response: User) => {
              console.log(response);
              this.message='';
              this.getClients();
              registrationForm.reset();
            },
            (error: HttpErrorResponse) => {
              this.message = error.error.message;
              //registrationForm.reset();
            }
          );
        }
      }
}
