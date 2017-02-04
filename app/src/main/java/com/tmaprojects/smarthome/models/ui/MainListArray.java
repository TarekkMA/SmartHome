package com.tmaprojects.smarthome.models.ui;

import com.tmaprojects.smarthome.Callbacks.SingleActionCallback;
import com.tmaprojects.smarthome.R;
import com.tmaprojects.smarthome.models.SwitchesGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TarekMA on 2/4/17.
 * facebook/tarekkma1
 */

public class MainListArray {
    private List<MainListItem> items;

    private int newSwitchPointer;

    private EmptyItem emptySwitches = new EmptyItem("There are no switches yet\nTry clicking ' + '");
    private EmptyItem emptyCameras = new EmptyItem("There are no cameras yet\nTry clicking ' + '");
    private EmptyItem emptyRemotes = new EmptyItem("There are no remotes yet\nTry clicking ' + '");


    public MainListArray(List<SwitchesGroup> switchesGroups) {

        List<MainListItem> list = new ArrayList<>();

        list.add(new IntroItem());

        //Alarm Button
        list.add(new IconTextItem(false, true, "Alarms", R.drawable.alarm_48, -1, new SingleActionCallback() {
            @Override
            public void call() {

            }
        }));

        list.add(new Header("Switches"));

        boolean isOdd = !(switchesGroups.size() % 2 == 0);
        for (SwitchesGroup group : switchesGroups) {
            list.add(newSwitchGroup(group));
        }

        //pointer to add new light_switch groups
        newSwitchPointer = list.size();

        if (switchesGroups.size() == 0)
            list.add(emptySwitches);

        list.add(new Header("Cameras"));
        list.add(emptyCameras);

        list.add(new Header("Remotes"));
        list.add(emptyRemotes);



        //Logout Button
        list.add(new IconTextItem(false, true, "Logout", R.drawable.logout_48, -1, new SingleActionCallback() {
            @Override
            public void call() {

            }
        }));

        items = list;
    }

    public int addSwitchGroup(SwitchesGroup group){
        try {
            if(get(newSwitchPointer) instanceof EmptyItem){
                items.set(newSwitchPointer, newSwitchGroup(group));
            }else {
                items.add(newSwitchPointer, newSwitchGroup(group));
            }
            return newSwitchPointer;
        }finally {
            newSwitchPointer++;
        }
    }
    public int removeSwitchGroup(int pos){
        items.remove(pos);
        newSwitchPointer--;
        if(get(newSwitchPointer-1) instanceof Header)items.add(newSwitchPointer,emptySwitches);
        return pos;
    }
    public int updateSwitchGroup(int pos,SwitchesGroup group){
        items.set(pos,newSwitchGroup(group));
        return pos;
    }

    public List<MainListItem> getItems() {
        return items;
    }

    public MainListItem get(int pos){
        return items.get(pos);
    }

    private MainListItem newSwitchGroup(SwitchesGroup group){
        IconTextItem i = new IconTextItem(false, false, group.getName(), -1, group.getColor(), new SingleActionCallback() {
            @Override
            public void call() {

            }
        });
        i.ref = group;
        return i;
    }
}
