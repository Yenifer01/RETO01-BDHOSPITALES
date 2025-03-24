import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistroHospitalesComponent } from './registro-hospitales.component';

describe('RegistroHospitalesComponent', () => {
  let component: RegistroHospitalesComponent;
  let fixture: ComponentFixture<RegistroHospitalesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RegistroHospitalesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RegistroHospitalesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
