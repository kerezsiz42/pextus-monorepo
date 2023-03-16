import { HttpClient, HttpParams } from '@angular/common/http';
import { Component } from '@angular/core';
import { Book, bookSchema } from '../books/books.component';

@Component({
  selector: 'find-books',
  templateUrl: './find-books.component.html',
})
export class FindBooksComponent {
  public endpoint = 'http://localhost:8080';
  public searchField: string = '';
  public books: Book[] = [];
  private timeout?: ReturnType<typeof setTimeout>;

  constructor(private http: HttpClient) {}

  public onInputChange(event: Event) {
    if (this.timeout !== undefined) {
      clearTimeout(this.timeout);
    }
    this.timeout = setTimeout(() => {
      const q = (<HTMLInputElement>event.target).value;
      if (q === '') {
        this.books = [];
        return;
      }
      const params = new HttpParams().set('q', q);
      this.http
        .get(`${this.endpoint}/api/v1/books`, { params })
        .subscribe((res) => {
          const result = bookSchema.array().safeParse(res);
          if (!result.success) {
            console.error(result.error);
            return;
          }
          this.books = result.data;
        });
    }, 400);
  }
}
