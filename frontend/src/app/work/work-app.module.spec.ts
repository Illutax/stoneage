import { TestBed } from '@angular/core/testing';
import { WorkAppModule } from './work-app.module';

describe(WorkAppModule.name, () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [WorkAppModule],
    });
  });

  it('should initialize', () => {
    expect(() => TestBed.inject(WorkAppModule)).not.toThrow();
  });
});
