import { TestBed } from '@angular/core/testing';

import { ArticoloService } from './articolo.service';

describe('ArticoloService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ArticoloService = TestBed.get(ArticoloService);
    expect(service).toBeTruthy();
  });
});
