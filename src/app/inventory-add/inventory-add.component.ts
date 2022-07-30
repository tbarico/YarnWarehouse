import { Component, OnInit } from '@angular/core';
import { InventoryApiService } from '../inventory-api.service';
import { Inventory } from '../models/inventory';

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

  constructor(private service :InventoryApiService) { }

  ngOnInit(): void {
  }

  addInventory(inventory :Inventory) {
    this.service.addInventory(inventory).subscribe(data => {this.inventory = data;});
  }

  test() {
    console.log("I've been clicked");
  }

  addInventoryFromForm(id :string, itemId :string, locationId :string, quantity :string) {
    var i = {
      inventoryId: id as unknown as number, 
      itemId: itemId as unknown as number,
      locationId: locationId as unknown as number,
      quantity: quantity as unknown as number,
      valid: true}
      this.addInventory(i);

  }
}
