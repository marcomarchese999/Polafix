import { Component, Input } from '@angular/core';
import { SerieServiceService } from 'src/app/serie-service.service';
import { UserService } from 'src/app/user.service';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-series',
  templateUrl: './series.component.html',
  styleUrls: ['./series.component.css'],
})
export class SeriesComponent {
  constructor(
    private serieService: SerieServiceService,
    private userService: UserService,
    private route: ActivatedRoute
  ) {}

  alphabet = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'.split('');
  searchTerm: string = '';
  serieList: any;
  email : any;
  errorMessage : string = '';
  error : boolean = false;

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.email = params.get('email');
    });
  }

  handleClick(letter: string) {
    this.serieService.getSerieByName(letter)
      .pipe(
        catchError((error) => {
          console.error('Errore durante la chiamata HTTP:', error);
          return throwError(error);
        })
      )
      .subscribe((data: any) => {
        this.serieList = Object.keys(data).map((key) => {
          return data[key];
        });
        console.log(this.serieList);
      },
      (err) => {this.printError()});
  }

  handleSearch() {
    if(this.searchTerm){
      this.serieService.getSerieByName(this.searchTerm)
      .pipe(
        catchError((error) => {
          console.error('Errore durante la chiamata HTTP:', error);
          return throwError(error);
        })
      )
      .subscribe((data: any) => {
        this.serieList = Object.keys(data).map((key) => {
          return data[key];
        });
        console.log(this.serieList);
      },
      (err) => {this.printError()});
    }
  }

  printError(){
    this.error = true;
    this.errorMessage = 'SERIE NOT FOUND';
  }

  addToCart(idSerie: number) {
    this.userService.addSerie(this.email, idSerie).pipe(
      catchError((error) => {
        console.error('Errore durante la chiamata HTTP:', error);
        return throwError(error);
      })
    )
    .subscribe((data: any) => {
      data = Object.keys(data).map((key) => {
        return data[key];
      });
      console.log(data)
    });
  }

  toggleDetails(name: string) {
    const serie = this.serieList.find((s: { name: string; }) => s.name === name);
    if (serie) {
      serie.showDetails = !serie.showDetails;
    }
  } 
}
