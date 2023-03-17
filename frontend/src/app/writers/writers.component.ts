import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { z } from 'zod';

const headers = new HttpHeaders({
  'Content-Type': 'application/json',
  Accept: 'application/json',
});

export const writerSchema = z.object({
  id: z.string(),
  fullName: z.string(),
  birthDate: z.string(),
});

export type Writer = z.infer<typeof writerSchema>;

@Component({
  selector: 'writers',
  templateUrl: './writers.component.html',
})
export class WritersComponent implements OnInit {
  public endpoint = 'http://localhost:8080';
  public idInputDisabled = true;
  public id = '';
  public fullName = '';
  public birthDate = '';
  public writers: Writer[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.fetchWriters();
  }

  public resetFields() {
    this.id = '';
    this.fullName = '';
    this.birthDate = '';
    this.idInputDisabled = true;
  }

  public fetchWriters() {
    this.http.get(`${this.endpoint}/api/v1/writers`).subscribe((res) => {
      const result = writerSchema.array().safeParse(res);
      if (!result.success) {
        console.error(result.error);
        return;
      }
      this.writers = result.data;
    });
  }

  public onUpdateWriter(writer: Writer) {
    this.id = writer.id;
    this.fullName = writer.fullName;
    this.birthDate = writer.birthDate;
  }

  public onInsertChange() {
    this.http
      .put(
        this.endpoint + '/api/v1/writers',
        JSON.stringify({
          ...(this.id && { id: this.id.trim() }),
          fullName: this.fullName.trim(),
          birthDate: this.birthDate.trim(),
        }),
        { headers }
      )
      .subscribe(() => {
        this.fetchWriters();
      });
  }

  public onDeleteWriter(id: string) {
    this.http.delete(`${this.endpoint}/api/v1/writers/${id}`).subscribe(() => {
      this.fetchWriters();
    });
  }
}
