import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { InventoryApiService } from '../inventory-api.service';
import { Inventory } from '../models/inventory';
import { Router } from '@angular/router';

/**
 * 
 * @author Tara Arico 7.29.2022
 */
@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.css']
})
export class InventoryComponent implements OnInit {

  inventoryList :Inventory[] = [];
  inventory? :Inventory;
  locId = '';

  constructor(private service :InventoryApiService, private route :ActivatedRoute, private url :Location, private router :Router) { }

  ngOnInit(): void {
    this.findInventoryFromUrl();
  }

  findInventoryAtLocation(locationId :string) {
    this.url.go(this.router.createUrlTree(['../'], {relativeTo: this.route})+'/'+locationId);
    this.service.findInventoryAtLocation(locationId).subscribe(data => {this.inventoryList = data});
  }

  findInventoryFromUrl() {
    const urlId = String(this.route.snapshot.paramMap.get('locationId'));
    this.findInventoryAtLocation(urlId);
  }

  goBack() {
    this.url.back();
  }

  getLocationId() {
    return this.locId;
  }

}
