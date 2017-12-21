package com.example.yychiu.atm;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;

public class AddActivity extends AppCompatActivity {

    private EditText edDate;
    private EditText edInfo;
    private EditText edAmount;
    private DatePicker datePicker;
    private AutoCompleteTextView autoCompleteTextView;
    private MyDBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        findview();
        helper= MyDBHelper.getInstance(this);
        setupAdapter();
    }

    private void setupAdapter() {
        Cursor cursor = helper.getReadableDatabase().query("infos",null,null,null,null,null,null);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_dropdown_item_1line,
                cursor,
                new String[]{"info"},
                new int[]{android.R.id.text1},0);
        adapter.setStringConversionColumn(1);
        autoCompleteTextView.setAdapter(adapter);
    }

    private void findview(){
        edDate = (EditText)findViewById(R.id.ed_date);
        edInfo = (EditText) findViewById(R.id.ed_info);
        edAmount = (EditText) findViewById(R.id.ed_amount);
        datePicker = (DatePicker)findViewById(R.id.datePicker);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
    }
    public void Add (View v){
//        String cdate = edDate.getText().toString();
        String cdate = String.valueOf(datePicker.getYear())+"-"+
                String.valueOf(datePicker.getMonth()+1)+"-"+
                String.valueOf(datePicker.getDayOfMonth());
        String info = edInfo.getText().toString();
        int amount = Integer.parseInt(edAmount.getText().toString());
        ContentValues values = new ContentValues();
        values.put("cdate",cdate);
        values.put("info",info);
        values.put("amount",amount);
        long id = helper.getWritableDatabase().insert("exp",null,values);

        Log.d("ADD",id+"");
        AddActivity.this.finish();
    }
}
