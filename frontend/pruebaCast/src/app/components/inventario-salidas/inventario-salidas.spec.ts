import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InventarioSalidas } from './inventario-salidas';

describe('InventarioSalidas', () => {
  let component: InventarioSalidas;
  let fixture: ComponentFixture<InventarioSalidas>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InventarioSalidas]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InventarioSalidas);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
