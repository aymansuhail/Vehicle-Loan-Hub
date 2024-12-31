import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit {
  // message:string
  constructor() { }

  ngOnInit(): void {
    // this.message = this.route.snapshot.queryParamMap.get('errorMsg');
  }
}
