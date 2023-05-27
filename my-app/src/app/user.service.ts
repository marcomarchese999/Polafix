import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  user:any;
  serieUser : any;

  constructor(private http: HttpClient) {}

  createUser(url: string, user: {}) {
    return this.http.post(url, user);
  }

  getAllUsers() {
    const url = `http://localhost:8080/users/`;
    return this.http.get(url);
  }

  getUserById(email: string) {
    const url = `http://localhost:8080/users/${email}`;
    return this.http.get(url);
  }

  getSerieUserInlist(email: string, id: number, season: number) {
    const url = `http://localhost:8080/users/${email}/inlist/${id}?season=`+season;
    return this.http.get(url);
  }

  seeChapterFromInlist(
    email: string,
    id: number,
    season: number,
    chapter: number
  ) {
    const url = `http://localhost:8080/users/${email}/inlist/${id}/${season}?chapter=${chapter}`;
    return this.http.put(url, {});
  }

  addSerie(email: string, id: number) {
    const url = `http://localhost:8080/users/${email}/inlist?id=${id}`;
    return this.http.put(url, {});
  }

  getCurrentBalance(email: string){
    const url = `http://localhost:8080/users/${email}/balances`;
    return this.http.get(url, {});
  }

  getSpecificBalance(email: string, year:number, month:string){
    const url = `http://localhost:8080/users/${email}/balances/${year}/${month}`;
    return this.http.get(url, {});
  }
}