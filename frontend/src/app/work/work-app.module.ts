import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WorkAppComponent } from './components/work-app/work-app.component';

@NgModule({
  declarations: [WorkAppComponent],
  imports: [CommonModule],
  exports: [WorkAppComponent],
})
export class WorkAppModule {}
