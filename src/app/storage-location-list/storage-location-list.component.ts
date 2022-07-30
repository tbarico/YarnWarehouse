import { Component, OnInit } from '@angular/core';
import { StorageLocation } from '../models/storageLocation';
import { StorageLocationApiService } from '../storage-location-api.service';

/**
 * 
 * @author Tara Arico 7.29.2022
 */
@Component({
  selector: 'app-storage-location-list',
  templateUrl: './storage-location-list.component.html',
  styleUrls: ['./storage-location-list.component.css']
})
export class StorageLocationListComponent implements OnInit {

  storageLocations :StorageLocation[] = [];
  storageLocation? :StorageLocation;

  constructor(private service :StorageLocationApiService) { }

  ngOnInit(): void {
    this.getLocations();
  }

  getLocations() {
    this.service.getLocations().subscribe(data => {this.storageLocations = data});
  }

}
