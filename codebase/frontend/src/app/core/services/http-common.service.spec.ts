import { TestBed } from '@angular/core/testing';

import { HttpCommonService } from './http-common.service';

describe('HttpCommonService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: HttpCommonService = TestBed.get(HttpCommonService);
    expect(service).toBeTruthy();
  });
});
