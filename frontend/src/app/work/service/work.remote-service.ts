import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable, of, switchMap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { WorkDto } from '../model/WorkDto';
import { Work } from '../model/Work';

@Injectable({
  providedIn: 'root',
})
export class WorkRemoteService {
  private readonly workEndPoint = `${environment.appBaseUrl}/work`;

  constructor(private readonly httpClient: HttpClient) {}

  public startWork(): Observable<Work> {
    return this.getWork().pipe(
      switchMap((it) => {
        return it !== null ? this.fallBackToCurrentlyRunning(it) : this.startWorkAtServer();
      }),
    );
  }

  private fallBackToCurrentlyRunning(it: Work) {
    console.warn('Already running:', it);
    return of(it);
  }

  private startWorkAtServer(): Observable<NonNullable<Work>> {
    return this.mapTo(this.httpClient.post<WorkDto>(`${this.workEndPoint}`, null)) as Observable<Work>;
  }

  public getWork(): Observable<Work | null> {
    return this.mapTo(this.httpClient.get<WorkDto>(`${this.workEndPoint}`));
  }
  private mapTo(dtos: Observable<WorkDto>): Observable<Work | null> {
    return dtos.pipe(map((dto) => (dto ? { ...dto, finishing: new Date(dto.finishing) } : null)));
  }
}
