import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InventoryComponent } from './inventory/inventory.component';
import { StorageLocationListComponent } from './storage-location-list/storage-location-list.component';
import { StorageLocationComponent } from './storage-location/storage-location.component';

const routes: Routes = [
  {path: 'locations', component: StorageLocationListComponent},
  {path: '', redirectTo: '/locations', pathMatch: 'full'},
  {path: 'inventory/:locationId', component: InventoryComponent },
  {path: 'inventory/:locationId', component: StorageLocationComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
