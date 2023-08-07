package io.codelex.flightplanner.models;

import java.util.ArrayList;

public class PageResult<T> {
    private int page = 0;
    private int totalItems;
    private ArrayList<T> items;

    public PageResult(int page, int totalItems, ArrayList<T> items) {
        this.page = page;
        this.totalItems = totalItems;
        this.items = items;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public ArrayList<T> getItems() {
        return items;
    }

    public void setItems(ArrayList<T> items) {
        this.items = items;
    }
}
