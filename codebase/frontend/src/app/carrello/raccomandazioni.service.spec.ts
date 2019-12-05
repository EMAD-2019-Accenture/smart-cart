import { TestBed } from '@angular/core/testing';

import { RaccomandazioniService } from './raccomandazioni.service';

describe('RaccomandazioniService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: RaccomandazioniService = TestBed.get(RaccomandazioniService);
    expect(service).toBeTruthy();
  });
});
