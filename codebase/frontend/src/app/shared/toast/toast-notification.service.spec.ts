import { TestBed } from '@angular/core/testing';

import { ToastNotificationService } from './toast-notification.service';

describe('ToastNotificationService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ToastNotificationService = TestBed.get(ToastNotificationService);
    expect(service).toBeTruthy();
  });
});
