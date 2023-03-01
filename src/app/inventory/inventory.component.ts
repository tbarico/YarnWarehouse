import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { InventoryApiService } from '../inventory-api.service';
import { Inventory } from '../models/inventory';
import { Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';

/**
 * 
 * @author Tara Arico 3.1.2023
 */
@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.css']
})
export class InventoryComponent implements OnInit {

  inventory? :Inventory;
  updateForm :boolean = false;
  updateInventoryForm = this.formBuilder.group({
    inventoryId: '',
    itemId: '',
    locationId: '',
    quantity: '',
    valid: true
  })

  constructor(private service :InventoryApiService, private route :ActivatedRoute, private url :Location, private router :Router, private formBuilder :FormBuilder) { }

  ngOnInit(): void {
    this.findInventoryFromUrl();
  }

  findInventory(inventoryId :string) {
    this.url.go(this.router.createUrlTree(['../'], {relativeTo: this.route})+'/'+inventoryId);
    this.service.findInventory(inventoryId).subscribe(data => {this.inventory = data});
  }

  findInventoryFromUrl() {
    const urlId = String(this.route.snapshot.paramMap.get('inventoryId'));
    this.findInventory(urlId);
  }

  goBack() {
    this.url.back();
  }

  updateInventory() {
    this.updateForm = true;
  }

  updateInventoryFromForm() {
    this.updateForm = false;
    var i = this.updateInventoryForm.value as unknown as Inventory;
    this.service.updateInventory(i).subscribe(data => {this.inventory = data;});
  }

  deleteInventory(inventory :Inventory) {
    this.service.deleteItemFromInventory(inventory).subscribe(data => {this.inventory = data;});
  }

}
