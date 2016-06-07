package com.viveksb007.pzldemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by viveksb007 on 6/7/16.
 */
public class History extends DialogFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View v = inflater.inflate(R.layout.history, container, false);
        DBHelper myDB = new DBHelper(getContext());
        ArrayList<String> ls = myDB.getAllHistory();
        if (ls.size() == 0) {
            Toast.makeText(getContext(), "No Code Scanned Yet.\nSuccessfully :P", Toast.LENGTH_SHORT).show();
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, ls);
        ListView lv = (ListView) v.findViewById(R.id.lv_history);
        lv.setAdapter(arrayAdapter);
        return v;
    }
}
