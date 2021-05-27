import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, Input } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { ClientService } from '../client/client.service';
import { User } from '../models/User';
import { HomeService } from './home.service';


@Component({ 
    templateUrl: 'home.component.html',
    styleUrls: ['./home.component.css'] })
export class HomeComponent{


  
  public message: string = '';

  constructor(private clientService: ClientService, private homeService: HomeService,  private router: Router){}


 

  onSubmit(loginForm: NgForm) {
      //alert(loginForm.controls['username'].value);
      //alert(loginForm.controls['password'].value);
      this.homeService.authenticate(loginForm.controls['username'].value, loginForm.controls['password'].value).subscribe(
        (response: Response) => {
          console.log(response);
          const obj = JSON.parse(JSON.stringify(response));
          console.log(obj.jwt);
          
          if(obj.role.length===2){
            console.log(obj.role[0].roleName)
            console.log(obj.role[1].roleName)
            sessionStorage.setItem("role", "ROLE_ADMIN");
            this.router.navigate(['client'])

          }
          else{
            console.log(obj.role[0].roleName)
            sessionStorage.setItem("role", obj.role[0].roleName);
            if(obj.role[0].roleName==='ROLE_CLIENT')
              this.router.navigate(['vehicle'])
              else 
              this.router.navigate(['client'])
          }
          sessionStorage.setItem("username", loginForm.controls['username'].value);
          sessionStorage.setItem("jwt", "Bearer "+obj.jwt);
      

        },
        (error: HttpErrorResponse) => {
         
        }
      
      );

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