import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InventoryComponent } from './inventory/inventory.component';
import { StorageLocationListComponent } from './storage-location-list/storage-location-list.component';

const routes: Routes = [
  {path: 'locations', component: StorageLocationListComponent},
  {path: '', redirectTo: '/locations', pathMatch: 'full'},
  {path: 'inventory/:locationId', component: InventoryComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
