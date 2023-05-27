import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { UserComponent } from './components/user/user.component';
import { SeriesComponent } from './components/series/series.component';
import { SerieUserComponent } from './components/serie-user/serie-user.component';
import { BalancesComponent } from './components/balances/balances.component';

const routes: Routes = [
  { path:  'Initio/:email', component:  UserComponent},
  { path: 'AgregarSerie/:email', component: SeriesComponent },
  { path: 'VerCargos/:email', component: BalancesComponent },
  { path: 'SerieUser/:email/:id/:season', component: SerieUserComponent },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],  
})
export class AppRoutingModule {

}
