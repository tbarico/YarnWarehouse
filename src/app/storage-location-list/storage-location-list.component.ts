import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { StorageLocation } from '../models/storageLocation';
import { StorageLocationApiService } from '../storage-location-api.service';

@Component({
  selector: 'app-storage-location-list',
  templateUrl: './storage-location-list.component.html',
  styleUrls: ['./storage-location-list.component.css']
})
export class StorageLocationListComponent implements OnInit {

  storageLocations :StorageLocation[] = [];
  storageLocation? :StorageLocation;

  constructor(private service :StorageLocationApiService, private url :Location) { }

  ngOnInit(): void {
    this.getLocations();
  }

  getLocations() {
    this.service.getLocations().subscribe(data => {this.storageLocations = data});
  }

  getLocation(locationId :string) {
    this.service.getLocation(locationId).subscribe(data => this.storageLocation = data);
  }

  goBack() :void {
    this.url.back();
  }

  ngOnDestroy(): void {}

}
