import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class SerieServiceService {
  constructor(private http: HttpClient) {}

  getSerieById(id: number) {
    const url = `http://localhost:8080/series/${id}`;
    return this.http.get(url);
  }

  getAllSeries(){
    const url = `http://localhost:8080/series/`;
    return this.http.get(url);
  }

  getSerieByName(name: string) {
    return this.http.get('http://localhost:8080/series?name=' + name);
  }
}
