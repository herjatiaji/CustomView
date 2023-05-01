package com.example.customview;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.res.ResourcesCompat;

import java.util.Locale;

public class EditTextWithClear extends AppCompatEditText {

    Drawable mClearButtonImage;
    Locale currentLocale = Locale.getDefault();
    String currentLanguageCode = currentLocale.getLanguage();
    String[] rtlLanguangesCode = {"ar", "he", "fa", "ur", "sd", "ps", "yi", "syr", "dv"};

    private void init(){

        mClearButtonImage = ResourcesCompat.getDrawable
                (getResources(), R.drawable.ic_clear_black_24dp, null);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (getCompoundDrawablesRelative()[2] != null ){
                    float clearButtonStartPosition =
                            (getWidth() - getPaddingEnd() -
                                    mClearButtonImage.getIntrinsicWidth());
                    float clearButtonEndPosition =
                            (getPaddingLeft() + mClearButtonImage.getIntrinsicWidth());
                    boolean isButtonClicked = false;

                    for (String rtlLanguage : rtlLanguangesCode) {
                        if (currentLanguageCode.equals(rtlLanguage)) {
                            if(motionEvent.getX() < clearButtonEndPosition){
                                isButtonClicked = true;
                            }
                            break;
                        }else{
                            if(motionEvent.getX() > clearButtonStartPosition){
                                isButtonClicked = true;
                            }
                        }
                    }

                    if (isButtonClicked){
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                            mClearButtonImage = ResourcesCompat.getDrawable
                                    (getResources(), R.drawable.ic_clear_black_24dp, null);
                        }
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                            mClearButtonImage = ResourcesCompat.getDrawable
                                    (getResources(), R.drawable.ic_clear_opaque_24dp, null);
                            getText().clear();
                            hideClearButton();
                            return true;
                        }
                    }
                }
                return false;
            }
        });

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                showClearButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public EditTextWithClear(@NonNull Context context) {
        super(context);
        init();
    }

    public EditTextWithClear(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextWithClear(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void showClearButton(){
        setCompoundDrawablesRelativeWithIntrinsicBounds
                (null, null, mClearButtonImage, null);
    }

    private void hideClearButton(){
        setCompoundDrawablesRelativeWithIntrinsicBounds
                (null, null, null, null);
    }


}