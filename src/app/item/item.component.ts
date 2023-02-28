import { Component, OnInit } from '@angular/core';
import { Item } from '../models/item';
import { ItemApiService } from '../item-api.service';
import { ActivatedRoute } from '@angular/router';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { FormBuilder } from '@angular/forms';

/**
 * 
 * @author Tara Arico 2.27.2023
 */
@Component({
  selector: 'app-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.css']
})
export class ItemComponent implements OnInit {

  item? :Item;
  updateForm :boolean = false;
  addForm :boolean = false;
  updateItemForm = this.formBuilder.group({
    itemId: '',
    categoryName: '',
    quantityTotal: '',
    price: '',
    valid: true
  })
  addItemForm = this.formBuilder.group({
    itemId: '',
    categoryName: '',
    quantityTotal: '',
    price: '',
    valid: true
  })

  constructor(private service :ItemApiService, private route :ActivatedRoute, private router :Router, private url :Location, private formBuilder :FormBuilder) { }

  ngOnInit(): void {
    this.findItemFromUrl();
  }

  findItem(itemId :String) {
    this.url.go(this.router.createUrlTree(['../'], {relativeTo: this.route})+'/'+itemId);
    this.service.findItem(itemId).subscribe(data => {this.item = data});
  }

  findItemFromUrl() {
    const urlId = String(this.route.snapshot.paramMap.get('itemId'));
    this.findItem(urlId);
  }

  showUpdateForm() {
    this.updateForm = true;
  }

  updateItemFromForm() {
    this.updateForm = false;
    this.updateItem(this.updateItemForm.value as unknown as Item);
  }

  updateItem(item :Item) {
    this.service.updateItem(item).subscribe(data => {this.item = data;});
    this.findItem(item.itemId as unknown as String);
  }

  showAddItemForm() {
    this.addForm = true;
  }

  addItemFromForm() {
    this.addForm = false;
    this.addItem(this.addItemForm.value as unknown as Item);
  }
  
  addItem(item :Item) {
    this.service.addItem(item).subscribe(data => {this.item = data;});
    this.findItem(item.itemId as unknown as String);
  }

  deleteItem(item :Item) {
    this.service.deleteItem(item).subscribe(data => {this.item = data;});
  }

}
