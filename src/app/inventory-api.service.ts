import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Inventory } from './models/inventory';

/**
 * 
 * @author Tara Arico 7.29.2022
 */
@Injectable({
  providedIn: 'root'
})
export class InventoryApiService {

  httpOptions = { headers :new HttpHeaders({'Content-Type': 'application/json'})};
  inventoryURL :string = 'inventory/';

  constructor(private http :HttpClient) { }

  findInventoryAtLocation(locationId :string) :Observable<any> { 
    const url = `inventory/${locationId}`;
    return this.http.get(environment.url+url);
  }

  addInventory(inventory :Inventory) :Observable<any> {
    return this.http.post(environment.url+this.inventoryURL+inventory.locationId, inventory, this.httpOptions);
  }

  deleteItemFromInventory(inventory :Inventory) :Observable<any> {
    const url = `inventoryId/${inventory.inventoryId}`;
    return this.http.delete(environment.url+url, this.httpOptions);
  }

  findInventory(inventoryId :string) :Observable<any> {
    const url = `inventoryId/${inventoryId}`;
    return this.http.get(environment.url+url);
  }

  updateInventory(inventory :Inventory) :Observable<any> {
    const url = `inventoryId/${inventory.inventoryId}`;
    return this.http.put(environment.url+url, inventory, this.httpOptions);
  }
}
