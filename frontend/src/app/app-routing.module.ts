import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BooksComponent } from './books/books.component';
import { FindBooksComponent } from './find-books/find-books.component';
import { WritersComponent } from './writers/writers.component';

const routes: Routes = [
  { path: 'books', component: BooksComponent },
  { path: 'writers', component: WritersComponent },
  { path: 'find-books', component: FindBooksComponent },
  { path: '**', redirectTo: '/books' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
