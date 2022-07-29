import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class StorageLocationApiService {
  locationURL :string = 'location/';
  locationsURL :string = 'locations/';
  //httpOptions = { headers :new HttpHeaders({'Content=Type': 'application/json'})};

  constructor(private http :HttpClient) { }

  getLocations() :Observable<any> {
    return this.http.get(environment.url+this.locationsURL);
  }

  getLocation(locationId :string) :Observable<any> {
    return this.http.get(environment.url+this.locationURL+locationId);
  }
}
