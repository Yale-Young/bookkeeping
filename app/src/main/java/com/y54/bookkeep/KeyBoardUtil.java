package com.y54.bookkeep;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.Date;

public class KeyBoardUtil {

    private KeyboardView keyboardView;
    private EditText editText;
    private Keyboard k1;// 自定义键盘
    public String date;
    public String value;
    public boolean Flag=false;
    public Context mContext;
    public String sign;

    public KeyBoardUtil(KeyboardView keyboardView, EditText editText, Context context,String s) {
        //setInputType为InputType.TYPE_NULL   不然会弹出系统键盘
        editText.setInputType(InputType.TYPE_NULL);
        k1 = new Keyboard(editText.getContext(), R.xml.keyboard_number);
        this.mContext = context;
        this.keyboardView = keyboardView;
        this.editText = editText;
        this.keyboardView.setOnKeyboardActionListener(listener);
        this.keyboardView.setKeyboard(k1);
        this.keyboardView.setEnabled(true);
        this.keyboardView.setPreviewEnabled(false);

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        date=sdf.format(new Date());
        sign = s;
    }

    private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {

        @Override
        public void swipeUp() {
        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onPress(int primaryCode) {
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable editable = editText.getText();
            int start = editText.getSelectionStart();

            switch (primaryCode) {
                case Keyboard.KEYCODE_DELETE:
                    if (editable != null && editable.length() > 0) {
                        if (start > 0) {
                            editable.delete(start - 1, start);
                        }
                    }
                    break;
                case Keyboard.KEYCODE_CANCEL:
                    keyboardView.setVisibility(View.GONE);
                    break;
                case Keyboard.KEYCODE_DONE :
                    //value = String.valueOf(Double.valueOf(editable.toString()));
                    value=editable.toString();
                    Log.d("out", value);
                    Intent intent = new Intent();
                    intent.setClass(mContext,MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("date",date);
                    bundle.putString("type","吃饭");
                    bundle.putString("io_type",sign);
                    if(sign.compareTo("out")==0) value = "-"+value;
                    bundle.putString("cost",value);
                    Log.d("budddddd", bundle.toString());
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    break;
                case -10://近日

                    DatePickerDialog dialog;
                    dialog = new DatePickerDialog(mContext);
                    dialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            String mm=String.valueOf(month+1),dd=String.valueOf(dayOfMonth);
                            if(month+1<10) mm = "0"+String.valueOf(month+1);
                            if(dayOfMonth<10) dd="0"+String.valueOf(dayOfMonth);
                            date = year+"-"+mm+"-"+dd;
                        }
                    });
                    dialog.show();
                    break;
                default:
                    editable.insert(start, Character.toString((char) primaryCode));
                    break;
            }

        }
    };

    // Activity中获取焦点时调用，显示出键盘
    public KeyBoardUtil showKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            keyboardView.setVisibility(View.VISIBLE);
        }
        return this;
    }

    // 隐藏键盘
    public void hideKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.VISIBLE|| visibility == View.INVISIBLE) {
            keyboardView.setVisibility(View.GONE);
        }
    }
}
