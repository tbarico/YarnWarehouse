import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InventoryComponent } from './inventory/inventory.component';
import { ItemComponent } from './item/item.component';
import { StorageLocationListComponent } from './storage-location-list/storage-location-list.component';
import { StorageLocationComponent } from './storage-location/storage-location.component';

const routes: Routes = [
  {path: 'locations', component: StorageLocationListComponent},
  {path: 'inventory/:locationId', component: InventoryComponent },
  {path: 'inventory/:locationId', component: StorageLocationComponent},
  {path: 'item/:itemId', component: ItemComponent},
  {path: '', redirectTo: '/locations', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
