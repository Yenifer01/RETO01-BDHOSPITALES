import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditarHospitalesComponent } from './editar-hospitales.component';

describe('EditarHospitalesComponent', () => {
  let component: EditarHospitalesComponent;
  let fixture: ComponentFixture<EditarHospitalesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditarHospitalesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EditarHospitalesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
