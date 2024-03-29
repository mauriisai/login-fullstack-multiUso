import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { UserService } from '../_services/user.service';
import { UserAuthService } from '../_services/UserAuthService';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  
  constructor(private userService:UserService, private userAuthService:UserAuthService, private router:Router) { }

  ngOnInit(): void {
  }

  login(loginForm:any ) {
    this.userService.login(loginForm.value).subscribe(
      (response:any) => {
        console.log(response.jwtToken);
        console.log(response.user.role);

        // Set en el localStorage el token generado y el rol del User
        this.userAuthService.setRoles(response.user.role);
        this.userAuthService.setToken(response.jwtToken);

        const role = response.user.role[0].roleName;
        console.log(role);
        if(role === 'Admin') {
          this.router.navigate(['/admin']);
        } else {
          this.router.navigate(['/user']);
        }

      }, (error) => {
        console.log(error)
      }
    );
  }

}
