package com.tmaprojects.smarthome.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.snappydb.DB;
import com.snappydb.KeyIterator;
import com.snappydb.SnappydbException;
import com.tmaprojects.smarthome.Callbacks.SingleActionCallback;
import com.tmaprojects.smarthome.MyApp;
import com.tmaprojects.smarthome.R;
import com.tmaprojects.smarthome.adapters.MainListAdapter;
import com.tmaprojects.smarthome.dialogs.NewSwitchGroupDialog;
import com.tmaprojects.smarthome.models.SwitchesGroup;
import com.tmaprojects.smarthome.models.ui.EmptyItem;
import com.tmaprojects.smarthome.models.ui.Header;
import com.tmaprojects.smarthome.models.ui.IconTextItem;
import com.tmaprojects.smarthome.models.ui.MainListArray;
import com.tmaprojects.smarthome.models.ui.MainListItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView list;
    int selectedColor = 0;
    MainListAdapter adapter;
    FloatingActionsMenu fabMenu;

    public static Intent getNewIntent(Context c){
        return new Intent(c,MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (RecyclerView)findViewById(R.id.main_list);
        final GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        list.setLayoutManager(layoutManager);
        adapter = new MainListAdapter(new MainListArray(getSavedCategories()));
        list.setAdapter(adapter);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch(adapter.getItemViewType(position)){
                    case MainListAdapter.ITEM_H_FULL:
                        return layoutManager.getSpanCount();
                    case MainListAdapter.HEADER:
                        return layoutManager.getSpanCount();
                    case MainListAdapter.EMPTY_TEXT:
                        return layoutManager.getSpanCount();
                    case MainListAdapter.INTRO_ITEM:
                        return layoutManager.getSpanCount();
                    default:
                        return 1;
                }
            }
        });


        fabMenu = (FloatingActionsMenu) findViewById(R.id.fab_menu);

        FloatingActionButton fabSwitches = (FloatingActionButton) findViewById(R.id.add_switchGroup);

        fabSwitches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                fabMenu.collapse();
                new NewSwitchGroupDialog(new NewSwitchGroupDialog.NewSwitchGroupCallback() {
                    @Override
                    public void groupCreated(SwitchesGroup group) {
                        try {
                            MyApp.getDBManager().getMainDB().put(group.getId(),group);
                            adapter.addItem(group);
                            Toast.makeText(MainActivity.this, "New Category has been added!", Toast.LENGTH_SHORT).show();
                        } catch (SnappydbException e) {
                            e.printStackTrace();
                        }
                    }
                }).show(MainActivity.this);
            }
        });


    }



    private List<SwitchesGroup> getSavedCategories(){
        DB db = MyApp.getDBManager().getMainDB();
        KeyIterator it=null;
        try {
            it = db.allKeysIterator();
            List<SwitchesGroup> categoryItems= new ArrayList<>();
            while (it.hasNext()){
                SwitchesGroup m = db.getObject(it.next(1)[0],SwitchesGroup.class);
                categoryItems.add(m);
            }
            return categoryItems;
        } catch (SnappydbException e) {
            e.printStackTrace();
        }finally {
            if(it!=null)it.close();
        }
        return new ArrayList<>();
    }

    @Override
    public void onBackPressed() {
        if(fabMenu.isExpanded()){
            fabMenu.collapse();
        }else {
            super.onBackPressed();
        }
    }
}
