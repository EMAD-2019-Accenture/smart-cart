import { TestBed } from '@angular/core/testing';

import { ToastService } from './toast.service';

describe('ToastNotificationService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ToastService = TestBed.get(ToastService);
    expect(service).toBeTruthy();
  });
});
