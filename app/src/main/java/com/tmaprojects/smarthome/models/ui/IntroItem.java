package com.tmaprojects.smarthome.models.ui;

import com.tmaprojects.smarthome.adapters.MainListAdapter;

/**
 * Created by TarekMA on 2/4/17.
 * facebook/tarekkma1
 */

public class IntroItem extends MainListItem {
    @Override
    public int getViewType() {
        return MainListAdapter.INTRO_ITEM;
    }
}
