import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard-admin',
  templateUrl: './dashboard-admin.component.html',
  styleUrls: ['./dashboard-admin.component.css']
})
export class DashboardAdminComponent implements OnInit {

  title = 'frontend';
  public term : string
  public isTokenThere : boolean

  constructor(private router: Router) {
      console.log("Token:  " + localStorage.getItem('token'));
      this.isTokenThere = localStorage.getItem('token') != null
  }
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

  search () {
      this.router.navigate(["/shop", this.term]).then(() => window.location.reload())
  }

  logOut () {
      localStorage.removeItem('token')
      this.router.navigateByUrl('/login').then(() => window.location.reload())
  }




}
