package com.cdac.dto;

import org.springframework.web.multipart.MultipartFile;

public class ItemDetails {

	private String itemName;
	private double itemPrice;
	private int availableNo;
	private MultipartFile multipartFile;

	public MultipartFile getMultipartFile() {
		return multipartFile;
	}

	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}

	public double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public int getAvailableNo() {
		return availableNo;
	}

	public void setAvailableNo(int availableNo) {
		this.availableNo = availableNo;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
		
	}

	
}
