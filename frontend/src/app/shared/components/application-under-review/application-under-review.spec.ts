import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplicationUnderReview } from './application-under-review';

describe('ApplicationUnderReview', () => {
  let component: ApplicationUnderReview;
  let fixture: ComponentFixture<ApplicationUnderReview>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ApplicationUnderReview]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ApplicationUnderReview);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
