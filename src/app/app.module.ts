import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { InventoryComponent } from './inventory/inventory.component';
import { StorageLocationListComponent } from './storage-location-list/storage-location-list.component';
import { InventoryAddComponent } from './inventory-add/inventory-add.component';
import { StorageLocationComponent } from './storage-location/storage-location.component';
import { ItemComponent } from './item/item.component';
import { InventoryAtLocationComponent } from './inventory-at-location/inventory-at-location.component';


@NgModule({
  declarations: [
    AppComponent,
    InventoryComponent,
    StorageLocationListComponent,
    InventoryAddComponent,
    StorageLocationComponent,
    ItemComponent,
    InventoryAtLocationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
