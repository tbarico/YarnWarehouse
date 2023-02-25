import { Component, OnInit } from '@angular/core';
import { InventoryApiService } from '../inventory-api.service';
import { Inventory } from '../models/inventory';
import { FormControl } from '@angular/forms';

/**
 * 
 * @author Tara Arico 7.30.2022
 */
@Component({
  selector: 'app-inventory-add',
  templateUrl: './inventory-add.component.html',
  styleUrls: ['./inventory-add.component.css']
})
export class InventoryAddComponent implements OnInit {
  inventory? :Inventory
  inventoryIdControl = new FormControl('');
  itemIdControl = new FormControl('');
  locationIdControl = new FormControl('');
  quantityControl = new FormControl('');

  constructor(private service :InventoryApiService) { }

  ngOnInit(): void {
  }

  addInventory(inventory :Inventory) {
    this.service.addInventory(inventory).subscribe(data => {this.inventory = data;});
  }

  addInventoryFromForm() {
    var i = {
      inventoryId: this.inventoryIdControl.value as unknown as number, 
      itemId: this.itemIdControl.value as unknown as number,
      locationId: this.locationIdControl.value as unknown as number,
      quantity: this.quantityControl.value as unknown as number,
      valid: true}
      this.addInventory(i);

  }
}
