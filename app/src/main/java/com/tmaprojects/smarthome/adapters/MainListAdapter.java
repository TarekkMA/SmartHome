package com.tmaprojects.smarthome.adapters;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.snappydb.SnappydbException;
import com.tmaprojects.smarthome.MyApp;
import com.tmaprojects.smarthome.R;
import com.tmaprojects.smarthome.Callbacks.SingleActionCallback;
import com.tmaprojects.smarthome.Utils;
import com.tmaprojects.smarthome.dialogs.DeleteDialog;
import com.tmaprojects.smarthome.dialogs.NewSwitchGroupDialog;
import com.tmaprojects.smarthome.models.SwitchesGroup;
import com.tmaprojects.smarthome.models.ui.EmptyItem;
import com.tmaprojects.smarthome.models.ui.Header;
import com.tmaprojects.smarthome.models.ui.IconTextItem;
import com.tmaprojects.smarthome.models.ui.MainListArray;
import com.tmaprojects.smarthome.models.ui.MainListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TarekMA on 1/31/17.
 * facebook/tarekkma1
 */

public class MainListAdapter extends RecyclerView.Adapter{

    public static final int ITEM_H_FULL = 57;
    public static final int HEADER = 950;
    public static final int ITEM_TEXT = 281;
    public static final int VOID_ITEM = 779;
    public static final int EMPTY_TEXT = 102;
    public static final int INTRO_ITEM = 308;

    MainListArray list;

    public MainListAdapter(MainListArray list) {
        this.list = list;
    }

    public void addItem(SwitchesGroup item){
        int i = list.addSwitchGroup(item);
        notifyItemInserted(i);
        notifyItemChanged(i);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType){
            case INTRO_ITEM:
                return new Intro_HV(inflater.inflate(R.layout.item_intro,parent,false));
            case ITEM_H_FULL:
                return new H_HV(inflater.inflate(R.layout.item_text_icon_h,parent,false));
            case HEADER:
                return new Header_HV(inflater.inflate(R.layout.item_header,parent,false));
            case ITEM_TEXT:
                return new Text_HV(inflater.inflate(R.layout.item_text,parent,false));
            case EMPTY_TEXT:
                return new Empty_HV(inflater.inflate(R.layout.item_empty,parent,false));
            case VOID_ITEM:
                return new Empty_HV(inflater.inflate(R.layout.item_empty,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder rawHolder, final int position) {

        switch (rawHolder.getItemViewType()){
            //using this syntax to avoid vars scopes problems :(
            case ITEM_H_FULL: {
                IconTextItem item = (IconTextItem)list.get(position);
                H_HV vh = (H_HV) rawHolder;
                vh.icon.setImageResource(item.getImgResId());
                vh.textView.setText(item.getText());
            }break;
            case HEADER: {
                Header item = (Header) list.get(position);
                Header_HV vh = (Header_HV) rawHolder;
                vh.textView.setText(item.getText());
            }break;
            case ITEM_TEXT: {
                final IconTextItem item = (IconTextItem)list.get(position);
                final Text_HV vh = (Text_HV) rawHolder;
                vh.cardView.setCardBackgroundColor(item.getColor());
                vh.textView.setText(item.getText()+position);
                if(Utils.isBrightColor(item.getColor())){
                    vh.textView.setTextColor(Color.BLACK);
                    vh.menu.setImageResource(R.drawable.menu_black_48);
                }
                vh.menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PopupMenu popup = new PopupMenu(vh.itemView.getContext(),vh.menu);
                        //Inflating the Popup using xml file
                        popup.getMenuInflater()
                                .inflate(R.menu.menu_edit_del, popup.getMenu());

                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem selectedMitem) {
                                if(selectedMitem.getItemId() == R.id.delete){
                                    new DeleteDialog("Do you want to delete this group?", new SingleActionCallback() {
                                        @Override
                                        public void call() {
                                            try {
                                                MyApp.getDBManager().getMainDB().del(((SwitchesGroup) item.ref).getId());
                                                notifyItemRemoved(list.removeSwitchGroup(position));
                                                notifyItemRangeChanged(position, getItemCount());//This to fix wrong positions
                                            } catch (SnappydbException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }).show(vh.itemView.getContext());
                                }else{
                                    new NewSwitchGroupDialog(((SwitchesGroup) item.ref), new NewSwitchGroupDialog.NewSwitchGroupCallback() {
                                        @Override
                                        public void groupCreated(SwitchesGroup group) {
                                            try {
                                                MyApp.getDBManager().getMainDB().put(group.getId(),group);
                                                int i = list.updateSwitchGroup(position,group);
                                                notifyItemChanged(i);
                                            } catch (SnappydbException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }).show(vh.itemView.getContext());
                                }
                                return true;
                            }
                        });

                        popup.show(); //showing popup menu
                    }

                });
            }break;
            case EMPTY_TEXT: {
                EmptyItem item = (EmptyItem)list.get(position);
                Empty_HV vh = (Empty_HV) rawHolder;
                vh.textView.setText(item.getText());
            }break;
        }

    }

    @Override
    public int getItemCount() {
        return list.getItems().size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getViewType();
    }

    public static class Text_HV extends RecyclerView.ViewHolder{
        public CardView cardView;
        public TextView textView;
        public ImageView menu;
        public Text_HV(View v) {
            super(v);
            cardView = (CardView)v.findViewById(R.id.item_card);
            textView = (TextView) v.findViewById(R.id.item_text);
            menu = (ImageView) v.findViewById(R.id.item_menu);
        }
    }

    public static class H_HV extends RecyclerView.ViewHolder{
        public CardView cardView;
        public TextView textView;
        public ImageView icon;
        public H_HV(View v) {
            super(v);
            cardView = (CardView)v.findViewById(R.id.item_card);
            textView = (TextView) v.findViewById(R.id.item_text);
            icon = (ImageView) v.findViewById(R.id.item_icon);
        }
    }

    public static class Header_HV extends RecyclerView.ViewHolder{
        TextView textView;
        public Header_HV(View v) {
            super(v);
            textView=(TextView)v.findViewById(R.id.item_text);
        }
    }

    public static class Empty_HV extends RecyclerView.ViewHolder{
        TextView textView;
        public Empty_HV(View v) {
            super(v);
            textView=(TextView)v.findViewById(R.id.item_text);
        }
    }
    public static class Intro_HV extends RecyclerView.ViewHolder{

        public Intro_HV(View v) {
            super(v);
        }
    }

}
