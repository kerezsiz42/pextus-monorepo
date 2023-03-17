import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { z } from 'zod';

const headers = new HttpHeaders({
  'Content-Type': 'application/json',
  Accept: 'application/json',
});

export const bookSchema = z.object({
  id: z.string(),
  publicationYear: z.number(),
  title: z.string(),
  writer: z.string(),
});

export type Book = z.infer<typeof bookSchema>;

@Component({
  selector: 'books',
  templateUrl: './books.component.html',
})
export class BooksComponent implements OnInit {
  public endpoint = 'http://localhost:8080';
  public idInputDisabled = true;
  public id = '';
  public publicationYear = '';
  public title = '';
  public writerId = '';
  public books: Book[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.fetchBooks();
  }

  public onResetFields() {
    this.id = '';
    this.publicationYear = '';
    this.title = '';
    this.writerId = '';
    this.idInputDisabled = true;
  }

  public fetchBooks() {
    this.http.get(`${this.endpoint}/api/v1/books`).subscribe((res) => {
      const result = bookSchema.array().safeParse(res);
      if (!result.success) {
        console.error(result.error);
        return;
      }
      this.books = result.data;
    });
  }

  public onUpdateBook(book: Book) {
    this.id = book.id;
    this.publicationYear = book.publicationYear.toString();
    this.title = book.title;
    this.writerId = book.writer;
    this.idInputDisabled = false;
  }

  public onInsertChange() {
    this.http
      .put(
        this.endpoint + '/api/v1/books',
        JSON.stringify({
          ...(this.id && { id: this.id.trim() }),
          publicationYear: parseInt(this.publicationYear),
          title: this.title.trim(),
          writerId: this.writerId.trim(),
        }),
        { headers }
      )
      .subscribe(() => {
        this.fetchBooks();
      });
  }

  public onDeleteBook(id: string) {
    this.http.delete(`${this.endpoint}/api/v1/books/${id}`).subscribe(() => {
      this.fetchBooks();
    });
  }
}
