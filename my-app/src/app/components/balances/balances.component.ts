import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/user.service';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-balances',
  templateUrl: './balances.component.html',
  styleUrls: ['./balances.component.css']
})
export class BalancesComponent {
  errorMessage : string = '';
  email : string = '';
  error : boolean = false;
  currentBalance : any;
  mese : string = '';
  anno : number = 0;
  amount : number = 0.00;
  months = [
    "JANUARY",
    "FEBRUARY",
    "MARCH",
    "APRIL",
    "MAY",
    "JUNE",
    "JULY",
    "AUGUST",
    "SEPTEMBER",
    "OCTOBER",
    "NOVEMBER",
    "DECEMBER"
  ];
  charges : any = [];
  

  constructor(private userService: UserService, private route: ActivatedRoute ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.email = params['email'];
    });

    console.log(this.email)

    this.userService.getCurrentBalance(this.email).pipe(
      catchError((error) => {
        console.error('Errore durante la chiamata HTTP iniziale:', error);
        return throwError(error);
      })
    )
    .subscribe((data: any) => {
      this.currentBalance = data;
      this.mese = this.currentBalance.month;
      this.anno = this.currentBalance.year;
      this.amount = this.currentBalance.amount;
      this.charges = this.currentBalance.charges;
      console.log(this.currentBalance);
    },
    (err) => {this.printError()});
  }

  previousBalance(){ 
    if(this.mese==='JANUARY'){
      this.mese = 'DECEMBER';
      this.anno = this.anno-1;
    }
    else
    {
      const monthIndex = this.months.indexOf(this.mese); 
      this.mese = this.months[monthIndex-1];
      this.anno = this.anno;
    }
    this.userService.getSpecificBalance(this.email, this.anno, this.mese).pipe(
    catchError((error) => {
      console.error('Errore durante la chiamata HTTP:', error);
      return throwError(error);
    })
  )
  .subscribe((data: any) => {
    this.currentBalance= Object.keys(data).map((key) => {
      return data[key];
    });
    this.currentBalance = data;
    this.amount = this.currentBalance.amount;
    this.charges = this.currentBalance.charges;
    console.log(this.currentBalance);
  },
  (err) => {this.printError()});
  }

  printError(){
    this.error = true;
    this.errorMessage = 'BALANCE NOT FOUND';
  }

  nextBalance(){
    if(this.mese=='DECEMBER'){
      this.mese = 'JANUARY';
      this.anno = this.anno+1;
    }
    else
    {
      const monthIndex = this.months.indexOf(this.mese); 
      console.log("mese attuale:", monthIndex);
      this.mese = this.months[monthIndex+1];
      this.anno = this.currentBalance.year;
    }
      this.userService.getSpecificBalance(this.email, this.anno, this.mese).pipe(
      catchError((error) => {
        console.error('Errore durante la chiamata HTTP:', error);
        return throwError(error);
      })
    )
    .subscribe((data: any) => {
      this.currentBalance= Object.keys(data).map((key) => {
        return data[key];
      });
      this.currentBalance = data;
      this.amount = this.currentBalance.amount;
      this.charges = this.currentBalance.charges;
      console.log(this.currentBalance);
    },
    (err) => {this.printError()});
  }

}
