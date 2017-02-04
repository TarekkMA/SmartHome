package com.tmaprojects.smarthome.models.ui;

import com.tmaprojects.smarthome.adapters.MainListAdapter;

/**
 * Created by TarekMA on 2/4/17.
 * facebook/tarekkma1
 */

public class Header extends MainListItem {
    private String text;
    private int iconResId;

    @Override
    public int getViewType() {
        return MainListAdapter.HEADER;
    }



    public Header(String text) {
        this.text = text;
    }

    public Header(String text, int iconResId) {
        this.text = text;
        this.iconResId = iconResId;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
