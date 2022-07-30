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
export class StorageLocationApiService {
  locationURL :string = 'location/';
  locationsURL :string = 'locations/';

  constructor(private http :HttpClient) { }

  getLocations() :Observable<any> {
    return this.http.get(environment.url+this.locationsURL);
  }
}
