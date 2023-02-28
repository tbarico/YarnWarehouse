import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Item } from './models/item';

/**
 * 
 * @author Tara Arico 2.27.2023
 */
@Injectable({
  providedIn: 'root'
})
export class ItemApiService {

  httpOptions = { headers :new HttpHeaders({'Content-Type': 'application/json'})};
  itemURL :string = 'item/';

  constructor(private http :HttpClient) { }

  findItem(itemId :String) :Observable<any> { 
    const url = `item/${itemId}`;
    return this.http.get(environment.url+url);
  }

  updateItem(item :Item) :Observable<any> {
    //const url = `item/`+item.itemId as unknown as string;
    const url = `item/${item.itemId}`;
    return this.http.put(environment.url+url, item, this.httpOptions).pipe(catchError(this.handleError));
  }

  addItem(item :Item) :Observable<any> {
    const url = `item/${item.itemId}`;
    return this.http.post(environment.url+url, item, this.httpOptions);
  }

  private handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(
        `Backend returned code ${error.status}, body was: `, error.error);
    }
    // Return an observable with a user-facing error message.
    return throwError(() => new Error('Something bad happened; please try again later.'));
  }
}
