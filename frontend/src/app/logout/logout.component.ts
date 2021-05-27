import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { HomeService } from '../home/home.service';
import { Router } from '@angular/router';


@Component({ templateUrl: 
    'logout.component.html',
    styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {

    constructor(
        private homeService: HomeService,
        private router: Router) {
    
      }

    ngOnInit() {
        this.homeService.logOut();
        this.router.navigate(['/']);
      }
}