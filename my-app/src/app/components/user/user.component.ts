import { Component } from '@angular/core';
import { UserService } from 'src/app/user.service';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
})
export class UserComponent {
  user: any;
  serieUser: any;
  searchEmail: string = '';
  isUserFound: boolean = true;
  name : string = '';
  surname : string ='';
  inlist : any = [];
  started : any = [];
  ended : any = [];
  errorMessage : string = '';
  error : boolean = false;
  tipoLista : string = '';

  constructor(private userService: UserService, private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.searchEmail = params['email'];
    });

    this.userService.getUserById(this.searchEmail)
      .pipe(
        catchError((error) => {
          console.error('Errore durante la chiamata HTTP:', error);
          return throwError(error);
        })
      )
      .subscribe((data: any) => {
        this.user = Object.keys(data).map((key) => {
          return data[key];
        });
        this.user = data;
        this.inlist = this.user.inlist;
        this.started = this.user.started;
        this.ended = this.user.ended;
        console.log(this.user);
      },
      (err) => {this.printError()});
  }

  printError(){
    this.error = true;
    this.errorMessage = 'USER NOT FOUND';
  }

  getSerieUserInlist(serie: any) {
    this.router.navigate(['SerieUser/'+this.searchEmail+ '/' + serie.id +'/'+ serie.currentSeason]);
  }
}