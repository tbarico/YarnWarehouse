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
}
