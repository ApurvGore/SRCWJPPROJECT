package com.cdac.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.dto.ItemDetails;
import com.cdac.dto.ItemStatus;
import com.cdac.dto.LoginStatus;
import com.cdac.dto.RegistrationStatus;
import com.cdac.dto.Status;
import com.cdac.entity.Employee;
import com.cdac.entity.Item;
import com.cdac.entity.Order;
import com.cdac.service.ItemService;
import com.cdac.utils.StorageService;


@RestController
@CrossOrigin
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@PostMapping(value = "/item/save", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ItemStatus save(@ModelAttribute ItemDetails itemDetails) {
//			storageService.store(itemDetails.getMultipartFile());
			Item savedItem = itemService.save(itemDetails);
			if(savedItem != null){
				ItemStatus status = new ItemStatus();
				status.setStatus(true);
				status.setStatusMessage("Item Saved!");
				status.setItemId(savedItem.getItem_id());
				return status;
			}
		
			else {
				ItemStatus status = new ItemStatus();
			status.setStatus(false);
			status.setStatusMessage("Item already exists");
			return status;		
			}
	}
	
	@PutMapping("/item/update/{itemName}")
	public ItemStatus update(@RequestBody Item item,@PathVariable String itemName) {
		
		Item itemCheck = itemService.update(item,itemName);
		if(itemCheck != null) {
			
			ItemStatus status = new ItemStatus();
			status.setStatus(true);
			status.setStatusMessage("Item Updated");
			status.setItemName(itemCheck.getItemName());
			status.setItemPrice(itemCheck.getItemPrice());
			status.setItemId(itemCheck.getItem_id());
			status.setAvailableNo(itemCheck.getAvailableNo());
			//status.setCustomer(customer);
			return status;
		}
		else  {
			ItemStatus status = new ItemStatus();
			status.setStatus(false);
			status.setStatusMessage("Error in Updation");
			return status;
		}
	}
	
	@DeleteMapping("/item/delete/{item_id}")
	public ItemStatus delete(@PathVariable int item_id) {
		
		boolean b = itemService.delete(item_id);
		if(b) {
			
			ItemStatus status = new ItemStatus();
			status.setStatus(true);
			status.setStatusMessage("Item Deleted");
			
			return status;
		}
		else  {
			ItemStatus status = new ItemStatus();
			status.setStatus(false);
			status.setStatusMessage("Item not Deleted");
			return status;
		}
	}
	
	@GetMapping("/item/fetch")
	public List<ItemStatus> fetch() {
		
		List<Item> items = itemService.fetch();
		List<ItemStatus> lst = new ArrayList<ItemStatus>();
		for (Item item : items) {
			ItemStatus status = new ItemStatus();
			status.setStatus(true);
			status.setStatusMessage("Items fetched");
			status.setItemId(item.getItem_id());
			status.setItemName(item.getItemName());
			status.setItemPrice(item.getItemPrice());
			status.setAvailableNo(item.getAvailableNo());
			status.setItemImage(item.getItemImage());
			lst.add(status);
			
		}
		return lst;
	}
	
	@GetMapping("/item/fetch/{itemName}")
	public ItemStatus fetchItemByName(@PathVariable String itemName) {
		
		Item itemCheck = itemService.fetchItemByName(itemName);
		if(itemCheck != null) {
			
			ItemStatus status = new ItemStatus();
			status.setStatus(true);
			status.setStatusMessage("Item Found");
			status.setItemName(itemCheck.getItemName());
			status.setItemPrice(itemCheck.getItemPrice());
			status.setItemId(itemCheck.getItem_id());
			status.setAvailableNo(itemCheck.getAvailableNo());
			return status;
		}
		else  {
			ItemStatus status = new ItemStatus();
			status.setStatus(false);
			status.setStatusMessage("Item Not found");
			return status;
		}
	}
	
}
