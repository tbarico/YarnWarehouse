import { TestBed } from '@angular/core/testing';

import { StorageLocationApiService } from './storage-location-api.service';

describe('StorageLocationApiService', () => {
  let service: StorageLocationApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StorageLocationApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
