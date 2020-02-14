import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { RaccomandazioniPageComponent } from './raccomandazioni-page.component';

describe('RaccomandazioniPageComponent', () => {
  let component: RaccomandazioniPageComponent;
  let fixture: ComponentFixture<RaccomandazioniPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RaccomandazioniPageComponent ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(RaccomandazioniPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
