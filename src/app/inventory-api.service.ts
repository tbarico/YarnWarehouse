import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

/**
 * 
 * @author Tara Arico 7.29.2022
 */
@Injectable({
  providedIn: 'root'
})
export class InventoryApiService {
  inventoryURL :string = 'inventory/';

  constructor(private http :HttpClient) { }

  findInventoryAtLocation(locationId :string) :Observable<any> { 
    const url = `inventory/${locationId}`;
    return this.http.get(environment.url+url);
  }
}
