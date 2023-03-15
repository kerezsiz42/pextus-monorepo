import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'books',
  templateUrl: './books.component.html',
})
export class BooksComponent implements OnInit {
  title = 'frontend';

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.http
      .get('http://localhost:8080/api/v1/books')
      .subscribe((res) => console.log(res));
  }
}
