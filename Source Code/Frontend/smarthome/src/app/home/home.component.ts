import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  providers: []
})


export class HomeComponent implements OnInit {
  public dataGet;

  constructor(private router: Router) {
  }

  ngOnInit() {
  }

  redirect(page: string) {
    this.router.navigate(['/' + page]);
  }
}
