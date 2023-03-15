import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { z } from 'zod';

const headers = new HttpHeaders({
  'Content-Type': 'application/json',
  Accept: 'application/json',
});

const bookSchema = z.object({
  id: z.string(),
  publicationYear: z.number(),
  title: z.string(),
  writer: z.string(),
});

type Book = z.infer<typeof bookSchema>;

@Component({
  selector: 'books',
  templateUrl: './books.component.html',
})
export class BooksComponent implements OnInit {
  endpoint = 'http://localhost:8080';
  public publicationYear = '';
  public title = '';
  public writerId = '';
  books: Book[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.fetchBooks();
  }

  fetchBooks() {
    this.http.get(this.endpoint + '/api/v1/books').subscribe((res) => {
      const result = bookSchema.array().safeParse(res);
      if (!result.success) {
        console.error(result.error);
        return;
      }
      this.books = result.data;
    });
  }

  update() {}

  addNew() {
    this.http
      .put(
        this.endpoint + '/api/v1/books',
        JSON.stringify({
          publicationYear: parseInt(this.publicationYear),
          title: this.title.trim(),
          writerId: this.writerId.trim(),
        }),
        { headers }
      )
      .subscribe((res) => {
        console.log(res);
      });
  }

  delete() {}
}
