import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { BarraNavigazionaleComponent } from './barra-navigazionale.component';

describe('BarraNavigazionaleComponent', () => {
  let component: BarraNavigazionaleComponent;
  let fixture: ComponentFixture<BarraNavigazionaleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BarraNavigazionaleComponent ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(BarraNavigazionaleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
