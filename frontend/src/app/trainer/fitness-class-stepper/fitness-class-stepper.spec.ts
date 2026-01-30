import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FitnessClassStepper } from './fitness-class-stepper';

describe('FitnessClassStepper', () => {
  let component: FitnessClassStepper;
  let fixture: ComponentFixture<FitnessClassStepper>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FitnessClassStepper]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FitnessClassStepper);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
