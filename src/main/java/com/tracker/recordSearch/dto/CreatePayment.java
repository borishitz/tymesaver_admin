package com.tracker.recordSearch.dto;

import com.google.gson.annotations.SerializedName;

public class CreatePayment {
    @SerializedName("item")
    Object[] items;
    public Object[] getItems() {
        return items;
    }
}
