package com.example.CSC_Mudassir.olxselling.model.datamodel;

public class Items {
    private String ItemName;
    private String ImageUri;
    private String ItemPrice;
    private String ItemDescription;
    private long timeStamp;

    public Items() {
    }

    public Items(String carName, String imageUri, String itemPrice, String itemDescription) {
        ItemName = carName;
        ImageUri = imageUri;
        ItemPrice = itemPrice;
        ItemDescription = itemDescription;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getItemName() {
        return ItemName;
    }

    public String getImageUri() {
        return ImageUri;
    }

    public void setImageUri(String imageUri) {
        ImageUri = imageUri;
    }

    public String getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(String itemPrice) {
        ItemPrice = itemPrice;
    }

    public String getItemDescription() {
        return ItemDescription;
    }

    public void setItemDescription(String itemDescription) {
        ItemDescription = itemDescription;
    }
}
