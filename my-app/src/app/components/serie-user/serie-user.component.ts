import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { UserService } from 'src/app/user.service';

@Component({
  selector: 'app-serie-user',
  templateUrl: './serie-user.component.html',
  styleUrls: ['./serie-user.component.css'],
})
export class SerieUserComponent {
  listChapters : any = [];
  email : any;
  serieUser : any;
  type : any;
  showDetail : boolean = true;
  season = 0;
  id : number =0;
  last_chapter : any;
  list_season : number[] = [];
  list_current_chapters : any = [];
  title : string = '';
  errorMessage : string = '';
  error : boolean = false;
  tipoLista : string = '';

  constructor(private userService: UserService, private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.email = params['email'];
      this.id = params['id'];
      this.season = params['season'];
    });

      this.userService.getSerieUserInlist(this.email, this.id, this.season)
      .pipe(
        catchError((error) => {
          console.error('Errore durante la chiamata HTTP:', error);
          return throwError(error);
        })
      )
      .subscribe((data: any) => {
        this.serieUser = Object.keys(data).map((key) => {
          return data[key];
        });
        console.log("SerieUser:", data);
        this.serieUser = data;
        this.title = this.serieUser.title;
        this.type = this.serieUser.serie.type;
        this.season=this.serieUser.currentSeason;
        this.listChapters = this.serieUser.userChapters;
        this.last_chapter = this.listChapters[this.listChapters.length - 1];
        for (let i = 1; i <= this.last_chapter.numberSeason; i++) {
          this.list_season.push(i);
        }
      }, 
      (err) => {this.printError()});
}
 
  previousSeason(){
    if(this.season-1>0){
      this.season=this.season-1;
    }
  }

  nextSeason(){
    if(this.season+1<=this.list_season.length){
      this.season=this.season+1;
    }
  }

  addChapter(num_chapter : number){
      this.userService.seeChapterFromInlist(this.email, this.id, this.season, num_chapter).pipe(
        catchError((error) => {
          console.error('Errore durante la chiamata HTTP addChapterSeenInList:', error);
          return throwError(error);
        })
      )
      .subscribe((data: any) => {
        this.serieUser = Object.keys(data).map((key) => {
          return data[key];
        });
        console.log(data);
        this.userService.serieUser = data;
        this.serieUser = this.userService.serieUser;
        this.listChapters = this.serieUser.userChapters;
    });
  }

  toggleDetails(chapterTitle: string) {
    const chapter = this.listChapters.find((chapter: any) => chapter.title === chapterTitle);
    if (chapter) {
      chapter.showDetails = !chapter.showDetails;
    }
  }

  printError(){
    this.error = true;
    this.errorMessage = 'SEASON NOT FOUND';
  }
  
}
