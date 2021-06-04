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
export class HomeComponent implements OnInit {


  public messageLogin: string = '';
  public message: string = '';
  public newClient: boolean = false;

  constructor(private clientService: ClientService, private homeService: HomeService,  private router: Router){}
  ngOnInit(): void {
    this.newClient = false;
  }


 

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
            this.router.navigate(['adminReview'])

          }
          else{
            console.log(obj.role[0].roleName)
            sessionStorage.setItem("role", obj.role[0].roleName);
            if(obj.role[0].roleName==='ROLE_CLIENT')
              this.router.navigate(['vehicle'])
              else 
              this.router.navigate(['adminReview'])
          }
          sessionStorage.setItem("username", loginForm.controls['username'].value);
          sessionStorage.setItem("jwt", "Bearer "+obj.jwt);
          this.messageLogin = '';

        },
        (error: HttpErrorResponse) => {
          this.messageLogin = error.error.message;

        }
      
      );

  }




    public onRegistration(registrationForm: NgForm): void {
      const closeBtn = document.getElementById('registration-form');
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
              if(closeBtn != null) closeBtn?.click()
              this.newClient = true;
            },
            (error: HttpErrorResponse) => {
              this.message = error.error.message;
              //registrationForm.reset();
            }
          );
        }
      }
}