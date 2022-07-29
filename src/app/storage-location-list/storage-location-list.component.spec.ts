import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StorageLocationListComponent } from './storage-location-list.component';

describe('StorageLocationListComponent', () => {
  let component: StorageLocationListComponent;
  let fixture: ComponentFixture<StorageLocationListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StorageLocationListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StorageLocationListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
