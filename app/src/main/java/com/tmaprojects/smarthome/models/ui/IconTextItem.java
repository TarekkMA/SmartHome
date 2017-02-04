package com.tmaprojects.smarthome.models.ui;

import com.tmaprojects.smarthome.Callbacks.SingleActionCallback;
import com.tmaprojects.smarthome.adapters.MainListAdapter;

/**
 * Created by TarekMA on 2/4/17.
 * facebook/tarekkma1
 */

public class IconTextItem extends MainListItem {
    private boolean isVertical;
    private boolean wight1;
    private String text;
    private int color;
    private int imgResId;
    private SingleActionCallback callback;
    public Object ref;

    public IconTextItem(boolean isVertical, boolean wight1, String text, int imgResId,int color, SingleActionCallback callback) {
        this.isVertical = isVertical;
        this.wight1 = wight1;
        this.text = text;
        this.imgResId = imgResId;
        this.color = color;
        this.callback = callback;
    }


    @Override
    public int getViewType() {
        if(wight1 && !isVertical){
            return MainListAdapter.ITEM_H_FULL;
        }else if(imgResId == -1){
            return MainListAdapter.ITEM_TEXT;
        }else {
            return -1;
        }
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isVertical() {
        return isVertical;
    }

    public void setVertical(boolean vertical) {
        isVertical = vertical;
    }

    public boolean isWight1() {
        return wight1;
    }

    public void setWight1(boolean wight1) {
        this.wight1 = wight1;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImgResId() {
        return imgResId;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }


}
