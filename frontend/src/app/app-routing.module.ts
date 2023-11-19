import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'work/app',
  },
  {
    path: 'work',
    children: [
      {
        path: 'app',
        loadChildren: () => import('./work/work-app.module').then((module) => module.WorkAppModule),
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
