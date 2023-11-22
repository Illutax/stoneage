import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { WorkAppModule } from './work/work-app.module';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule, //
    AppRoutingModule,
    HttpClientModule,
    WorkAppModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
