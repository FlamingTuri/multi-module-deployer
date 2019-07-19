import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MicroServiceInteractionComponent } from './micro-service-interaction.component';

describe('MicroServiceInteractionComponent', () => {
  let component: MicroServiceInteractionComponent;
  let fixture: ComponentFixture<MicroServiceInteractionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MicroServiceInteractionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MicroServiceInteractionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
