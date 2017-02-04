package com.tmaprojects.smarthome.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.snappydb.SnappydbException;
import com.tmaprojects.smarthome.MyApp;
import com.tmaprojects.smarthome.R;
import com.tmaprojects.smarthome.models.SwitchesGroup;

import java.util.UUID;

/**
 * Created by TarekMA on 2/4/17.
 * facebook/tarekkma1
 */

public class NewSwitchGroupDialog{
    private int selectedColor;
    private SwitchesGroup group;
    private NewSwitchGroupCallback callback;

    public interface NewSwitchGroupCallback{void groupCreated(SwitchesGroup group);}



    public NewSwitchGroupDialog(SwitchesGroup group, NewSwitchGroupCallback callback) {
        this.group = group;
        this.selectedColor = selectedColor;
        this.callback = callback;
    }

    public NewSwitchGroupDialog(NewSwitchGroupCallback callback) {
        this.callback = callback;
    }

    public void show(final Context c) {
        MaterialDialog md = new MaterialDialog.Builder(c)
                .title("Add Category")
                .customView(R.layout.dialog_newcategory, false)
                .positiveText("Add")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        String name = ((EditText) dialog.findViewById(R.id.dialog_name)).getText().toString();
                        if(name.isEmpty()){
                            Toast.makeText(c, "Name can't be empty!", Toast.LENGTH_SHORT).show();
                        }else {
                            SwitchesGroup catItem = new SwitchesGroup(name, selectedColor, (group==null)?UUID.randomUUID().toString():group.getId());
                            if(callback!=null)callback.groupCreated(catItem);
                        }
                    }
                })
                .negativeText("Cancel")
                .build();
        View v = md.getView();
        int [] colors = c.getResources().getIntArray(R.array.color_array);
        final View colorSwatch = v.findViewById(R.id.dialog_colorswatch);
        HorizontalScrollView colorPalette = (HorizontalScrollView)v.findViewById(R.id.dialog_palette);
        LinearLayout paletteContainer = new LinearLayout(c);
        paletteContainer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        if(group!=null){
            ((EditText) md.findViewById(R.id.dialog_name)).setText(group.getName());
            selectedColor=group.getColor();
        }else{
            selectedColor = colors[0];
        }
        colorSwatch.setBackgroundColor(selectedColor);
        for (final int color : colors) {
            FrameLayout cF = (FrameLayout) LayoutInflater.from(c).inflate(R.layout.item_color,null);
            cF.getChildAt(0).setBackgroundColor(color);
            cF.getChildAt(0).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    colorSwatch.setBackgroundColor(color);
                    selectedColor = color;
                }
            });
            paletteContainer.addView(cF);
        }
        colorPalette.addView(paletteContainer);
        md.show();
    }
}
