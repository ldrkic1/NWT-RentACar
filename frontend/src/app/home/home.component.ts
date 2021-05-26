import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ClientService } from '../client/client.service';
import { User } from '../models/User';

@Component({ 
    templateUrl: 'home.component.html',
    styleUrls: ['./home.component.css'] })
export class HomeComponent {
  public message: string = '';
  constructor(private clientService: ClientService){}

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