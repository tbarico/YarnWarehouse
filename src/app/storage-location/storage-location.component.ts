import { getLocaleDirection } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { StorageLocation } from '../models/storageLocation';
import { StorageLocationApiService } from '../storage-location-api.service';

@Component({
  selector: 'app-storage-location',
  templateUrl: './storage-location.component.html',
  styleUrls: ['./storage-location.component.css']
})

/**
 * 
 * @author Tara Arico 7.30.2022
 */
export class StorageLocationComponent implements OnInit {

  storageLocation? :StorageLocation;

  @Input() 
  set onChange(id: string) {
    this.getLocation(id);
  }

  constructor(private service :StorageLocationApiService, private route :ActivatedRoute) { }

  ngOnInit(): void {
    this.getLocationFromUrl();
  }

  getLocation(locationId :string) {
    this.service.getLocation(locationId).subscribe(data => {this.storageLocation = data});
  }

  getLocationFromUrl() {
    const urlId = String(this.route.snapshot.paramMap.get('locationId'));
    this.getLocation(urlId);
  }

}
