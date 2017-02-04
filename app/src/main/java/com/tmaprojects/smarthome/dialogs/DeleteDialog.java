package com.tmaprojects.smarthome.dialogs;

import android.content.Context;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.tmaprojects.smarthome.Callbacks.SingleActionCallback;

/**
 * Created by TarekMA on 2/4/17.
 * facebook/tarekkma1
 */

public class DeleteDialog{
    SingleActionCallback callback;
    String title;


    public DeleteDialog(String title,SingleActionCallback callback) {
        this.title = title;
        this.callback = callback;
    }

    public void show(Context c) {
        MaterialDialog mdDel = new MaterialDialog.Builder(c)
                .title(title)
                .positiveText("YES")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        callback.call();
                    }
                }).build();
        mdDel.show();
    }
}
