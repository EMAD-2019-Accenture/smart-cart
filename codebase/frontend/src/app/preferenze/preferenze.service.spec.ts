import { TestBed } from '@angular/core/testing';

import { PreferenzeService } from './preferenze.service';

describe('PreferenzeService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PreferenzeService = TestBed.get(PreferenzeService);
    expect(service).toBeTruthy();
  });
});
