import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkAppComponent } from './work-app.component';

describe('WorkAppComponent', () => {
  let component: WorkAppComponent;
  let fixture: ComponentFixture<WorkAppComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [WorkAppComponent],
    });
    fixture = TestBed.createComponent(WorkAppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
