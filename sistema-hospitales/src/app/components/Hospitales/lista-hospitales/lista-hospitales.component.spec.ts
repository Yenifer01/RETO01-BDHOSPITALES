import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaHospitalesComponent } from './lista-hospitales.component';

describe('ListaHospitalesComponent', () => {
  let component: ListaHospitalesComponent;
  let fixture: ComponentFixture<ListaHospitalesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListaHospitalesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ListaHospitalesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
