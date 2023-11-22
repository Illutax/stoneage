import { TestBed } from '@angular/core/testing';

import { WorkRemoteService } from './work.remote-service';
import { MockBuilder } from 'ng-mocks';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { environment } from '../../../environments/environment';
import { WorkDto } from '../model/WorkDto';
import { Work } from '../model/Work';

describe('WorkRemoteServiceService', () => {
  beforeEach(() => {
    const ngModule = MockBuilder(WorkRemoteService) //
      .keep(HttpClientTestingModule)
      .build();
    TestBed.configureTestingModule(ngModule);
    return TestBed.compileComponents();
  });

  it('should be created', () => {
    const service = TestBed.inject(WorkRemoteService);
    expect(service).toBeTruthy();
  });

  it('has work should get work', () => {
    const service = TestBed.inject(WorkRemoteService);
    const controller = TestBed.inject(HttpTestingController);
    const response = {
      finishing: '2023-11-21T22:54:39.249744',
      completed: false,
    } as WorkDto;
    const result = {
      finishing: new Date('2023-11-21T22:54:39.249744'),
      completed: false,
    } as Work;
    const callback = jest.fn();

    service.getWork().subscribe(callback);

    controller.expectOne(`${environment.appBaseUrl}/work`).flush(response);
    expect(callback).toHaveBeenCalledWith(result);
  });

  it('has no work should get work', () => {
    const service = TestBed.inject(WorkRemoteService);
    const controller = TestBed.inject(HttpTestingController);
    const response = {} as WorkDto;
    const callback = jest.fn();

    service.getWork().subscribe(callback);

    controller.expectOne(`${environment.appBaseUrl}/work`).flush(response);
    expect(callback).toHaveBeenCalledWith({});
  });
});
