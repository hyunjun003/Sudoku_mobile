package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.shape.CutCornerTreatment;
import com.google.android.material.tabs.TabLayout;

import java.time.temporal.ChronoUnit;

public class MainActivity extends AppCompatActivity {
    TableLayout table, numberPad;

    CustomButton[][] buttons = new CustomButton[9][9];
    BoardGenerator board = new BoardGenerator();
    Button numP1, numP2, numP3, numP4, numP5, numP6, numP7, numP8, numP9, cancel_button, delete_button;
    CustomButton selectedB;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        table = (TableLayout) findViewById(R.id.tableLayout);
        numberPad = (TableLayout) findViewById(R.id.numberPad);
        makeBoard();
        insertNumPad();
    }

    public void insertNumPad() {
        numP1 = findViewById(R.id.numP1);
        numP1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEvent(view, 1);
            }
        });
        numP2 = findViewById(R.id.numP2);
        numP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEvent(view, 2);
            }
        });
        numP3 = findViewById(R.id.numP3);
        numP3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEvent(view, 3);
            }
        });
        numP4 = findViewById(R.id.numP4);
        numP4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEvent(view, 4);
            }
        });
        numP5 = findViewById(R.id.numP5);
        numP5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEvent(view, 5);
            }
        });
        numP6 = findViewById(R.id.numP6);
        numP6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEvent(view, 6);
            }
        });
        numP7 = findViewById(R.id.numP7);
        numP7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEvent(view, 7);
            }
        });
        numP8 = findViewById(R.id.numP8);
        numP8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEvent(view, 8);
            }
        });
        numP9 = findViewById(R.id.numP9);
        numP9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEvent(view, 9);
            }
        });
        cancel_button = findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPad.setVisibility(View.INVISIBLE);
            }
        });

        delete_button = findViewById(R.id.delete_button);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedB.reset();
                numberPad.setVisibility(View.INVISIBLE);
                selectedB.setBackgroundColor(Color.WHITE);
            }
        });
    }

    // numPad에서 숫자 클릭시 빈칸에 넣어주는 부분
    public void onClickEvent(View view, int a) {
        selectedB.insertNum(a);
        numberPad.setVisibility(View.INVISIBLE);
        setColor(conflictTest(a));
    }

    //gameBoard 생성
    public void makeBoard() {
        TableRow tableRow;
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                1.0f
        );
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                1.0f);
        tableParams.setMargins(15, 15, 15, 15);
        for (int i = 0; i < 9; i++) {

            tableRow = new TableRow(this);
            tableRow.setLayoutParams(tableParams);

            for (int j = 0; j < 9; j++) {
                int finalI = i;
                int finalJ = j;
                layoutParams.setMargins(5, 5, 5, 5);
                buttons[i][j] = new CustomButton(this, i, j);
                buttons[i][j].setLayoutParams(layoutParams);
// 70% 채우기 시작
                int randomInt = (int) (Math.random() * 10);
                if (randomInt > 3) {
                    buttons[i][j].set(board.get(i, j));
                    buttons[i][j].setClickable(false);
                } else {
                    buttons[i][j].setNone("");
                    buttons[i][j].setClickable(true);
                    //dialog
                    buttons[i][j].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            selectedB = buttons[finalI][finalJ];
                            numberPad.setVisibility(View.VISIBLE);
                        }
                    });
                }
                tableRow.addView(buttons[i][j]);
            }
            table.addView(tableRow);
        }

    }


    public int conflictTest(int testNum) {
        int selectRow = selectedB.row;
        int selectCol = selectedB.col;
        CustomButton NumberCustom;//row
        CustomButton NumberCustom2;//col
        CustomButton NumberCustomMe = buttons[selectRow][selectCol];;
        CustomButton NumberCustom3; //3x3
        for (int c = 0; c < 9; c++) {//row col 검사
            //row에 같은값 있는지 검사
            NumberCustom = buttons[selectRow][c];
            NumberCustom2 = buttons[c][selectCol];
            if (testNum == NumberCustom.get() && c != selectCol) {//row 값을 검사함 ( 같은 숫자가 있는지 확인 )
//                setConflict(NumberCustomMe);
                System.out.println("Row");
                return 1;
            }
            else if (testNum == NumberCustom2.get() && c != selectRow) {//col 값을 검사함 ( 같은 숫자가 있는지 확인 )
//                setConflict(NumberCustomMe);
                System.out.println("Col");
                return 1;
            }
        }
        int tmpRow = selectRow/3*3;
        int tmpCol = selectCol/3*3;
        for (int r =tmpRow; r<=tmpRow+2; r++){
            for (int c=tmpCol; c<=tmpCol+2; c++){
                NumberCustom3=buttons[r][c];
                if (testNum == NumberCustom3.get() && r!=selectRow && c !=selectCol){
//                    setConflict(NumberCustomMe);
                    System.out.println("3x3");
                    return 1;
                }
            }
        }
        return 0;
    }

    public void setColor(int status){
        System.out.println(status);
        if(status==1){
            setConflict();
        }
        else{
            unsetConflict();
        }
    }


    public void setConflict() {
        selectedB.setBackgroundColor(Color.RED);
    }

    public void unsetConflict() {
        selectedB.setBackgroundColor(Color.WHITE);
    }
}
