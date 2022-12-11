package com.example.sudoku;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;


public class CustomButton extends FrameLayout {

    int row;
    int col;
    int value;
    String C;
    TextView textView;
    public CustomButton(@NonNull Context context, int row, int col) {
        super(context);
        this.row = row;
        this.col = col;
        textView = new TextView(context);
        textView.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
        textView.setTextColor(Color.BLUE);
        setBackgroundResource(R.drawable.button_selector);
        setClickable(true);
        addView(textView);

    }
    //make Board Start
    //빈칸 채우기
    public void set(int num) {
        value = num;
        textView.setText(num + "");
    }
    //빈칸 삽입
    public void setNone(String C){
        this.C = C;
        textView.setText("");
    }
    //make Board End

    //insert 숫자 넣는 부분
    public void insertNum(int N){
        value = N;
        textView.setTextColor(Color.BLACK);
        textView.setText(N+"");
    }
    //insert 숫자 넣는 부분 End




    public int get() {

        return value;
    }

    void reset(){
        textView.setText("");
    }

}
