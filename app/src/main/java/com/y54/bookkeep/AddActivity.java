package com.y54.bookkeep;

import android.annotation.TargetApi;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.os.Bundle;

import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AddActivity extends AppCompatActivity {
    private RadioButton rbOut,rbIn,rbcf,rbsj,rbgz,rbqt;
    private RadioGroup RGout,RGin;
    String ty;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_dialog);
        initView();
    }

    private void initView(){
        rbIn = (RadioButton)findViewById(R.id.rb_in);
        rbOut = (RadioButton)findViewById(R.id.rb_out);
        rbcf = (RadioButton)findViewById(R.id.rb_cf);
        rbsj = (RadioButton)findViewById(R.id.rb_sj);
        rbgz = (RadioButton)findViewById(R.id.rb_gz);
        rbqt = (RadioButton)findViewById(R.id.rb_qt);
        RGout = (RadioGroup)findViewById(R.id.RG_type_out);
        RGin = (RadioGroup)findViewById(R.id.RG_type_in);


        rbOut.setChecked(true);

        final KeyboardView keyboard = (KeyboardView) findViewById(R.id.kv_keyboard);
        final EditText editText = (EditText) findViewById(R.id.et);
        RGout.animate().translationX(-1080).setDuration(2000).start();

        RGout.setVisibility(View.INVISIBLE);
        RGin.setVisibility(View.VISIBLE);
        RGin.animate().translationY(-100).setDuration(1000).start();
        if(rbIn.isChecked()){
            ty = "in";

        } else if(rbOut.isChecked()){
            ty = "out";
        }

        KeyBoardUtil keyBoardUtil = new KeyBoardUtil(keyboard,editText,AddActivity.this,ty).showKeyboard();


        //取消记一笔
//        btCl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(AddActivity.this,MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        //记完了
        if(false){
            Intent intent = new Intent();
            intent.setClass(AddActivity.this,MainActivity.class);
            Bundle bundle = new Bundle();
            Log.d("budddddd", bundle.toString());
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }


}
