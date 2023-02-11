package com.feelingk.iap.gui.data;

public class PurchaseItem {
    public boolean bUseTCash;
    public String itemName;
    public int itemPrice;
    public int itemPurchasePrice;
    public int itemTCash;
    public String itemUseDate;

    public PurchaseItem(String name, String date, int price, int cash, int purchasePrice, boolean useTCash) {
        this.itemName = name;
        this.itemUseDate = date;
        this.itemPrice = price;
        this.itemTCash = cash;
        this.itemPurchasePrice = purchasePrice;
        this.bUseTCash = useTCash;
    }
}
