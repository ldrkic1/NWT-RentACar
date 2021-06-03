import { Component, OnInit } from '@angular/core';
import { ClientComponent } from '../client';
import { NgForm } from '@angular/forms';


@Component({ 
    templateUrl: 'signup.component.html',
    styleUrls: ['./signup.component.css'] })
export class SignUpComponent implements OnInit{
    constructor(public clientComponent: ClientComponent){}
    public message: string = '';
    public onRegistration(registrationForm: NgForm): void {
        this.clientComponent.onRegistration(registrationForm);
    }
    ngOnInit() {
     
      }
}