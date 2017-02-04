package com.tmaprojects.smarthome.models.ui;

import com.tmaprojects.smarthome.adapters.MainListAdapter;

/**
 * Created by TarekMA on 2/4/17.
 * facebook/tarekkma1
 */

public class EmptyItem extends MainListItem {

    private String text;

    public EmptyItem() {
    }

    public EmptyItem(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int getViewType() {
        return (text==null)? MainListAdapter.VOID_ITEM:MainListAdapter.EMPTY_TEXT;
    }
}
