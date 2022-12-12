package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class MainActivity extends AppCompatActivity {
    TableLayout table, numberPad;

    CustomButton[][] buttons = new CustomButton[9][9];
    BoardGenerator board = new BoardGenerator();
    Button numP1, numP2, numP3, numP4, numP5, numP6, numP7, numP8, numP9, cancel_button, delete_button;
    CustomButton selectedB;
    Dialog dialog;
    Button reset_button;
    boolean[][] boardLog = new boolean[9][9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        table = (TableLayout) findViewById(R.id.tableLayout);
        numberPad = (TableLayout) findViewById(R.id.numberPad);
        reset_button = findViewById(R.id.reset_button);
        makeBoard();
        insertNumPad();
        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetBoard();
            }
        });
    }
    public void resetBoard(){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if (boardLog[i][j]!=true)
                {
                    buttons[i][j].reset();
                    unConflicts(buttons[i][j]);               // 색상 변경
                }

            }
        }
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
                    boardLog[i][j]=true;
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
//        CustomButton NumberCustomMe = buttons[selectRow][selectCol];
//        CustomButton NumberCustom3 = null; //3x3
        for (int c = 0; c < 9; c++) {//row col 검사
            NumberCustom = buttons[selectRow][c];
            NumberCustom2 = buttons[c][selectCol];
            if (testNum == NumberCustom.get() && c != selectCol) {//row 값을 검사 ( 같은 숫자가 있는지 확인 )
                if (testNum == NumberCustom2.get() && c != selectRow) {//col 값을 검사 ( 같은 숫자가 있는지 확인 )
                    int a = threeFinder(selectRow, selectCol, testNum, null);//row col 걸린상태에서 3.3도 검사함 ( 붉은색으로 만들기 위함 )
//                    Conflicts(buttons[c][selectCol]);
                    return 1+a;
                } else {
                    int a = threeFinder(selectRow, selectCol, testNum, null);
//                    Conflicts(buttons[selectRow][c]);
                    return 1+a;
                }
            }
            else{
//                unConflicts(buttons[selectRow][c]);
            }

            if (testNum == NumberCustom2.get() && c != selectRow) {//col 값을 검사 ( 같은 숫자가 있는지 확인 )
                if (testNum == NumberCustom.get() && c != selectCol) { // row 값을 검사
                    int a = threeFinder(selectRow, selectCol, testNum, null);
//                    Conflicts(buttons[selectRow][c]);
                    return 1+a;
                } else {
                    int a = threeFinder(selectRow, selectCol, testNum, null);
//                    Conflicts(buttons[c][selectCol]);
                    return 1+a;
                }
            }
            else{
//                unConflicts(buttons[c][selectCol]);
            }
        }
        return 0;
    }
    //3x3 finder test
    public int threeFinder(int selectRow, int selectCol, int testNum, CustomButton NumberCustom3){
        int tmpRow = selectRow / 3 * 3;
        int tmpCol = selectCol / 3 * 3;
        for (int r = tmpRow; r <= tmpRow + 2; r++) {
            for (int c = tmpCol; c <= tmpCol + 2; c++) {
                NumberCustom3 = buttons[r][c];
                if (testNum == NumberCustom3.get() && r+c != selectRow + selectCol) {
//                    Conflicts(buttons[r][c]);
                    return 1;
                }
//                unConflicts(buttons[r][c]);
            }
        }
        return 0;
    }

    public void setColor(int status) {
        System.out.println("status "+status);
        if (status >= 1) {
            setConflict();
        } else {
            unsetConflict();
        }
    }

    public void setConflict() {
        selectedB.setBackgroundColor(Color.RED);
    }
    public void unsetConflict() {
        selectedB.setBackgroundColor(Color.WHITE);
    }
//
//    public void Conflicts(CustomButton customButton){
//        customButton.setBackgroundColor(Color.RED);
//    }
    public void unConflicts(CustomButton customButton){
        customButton.setBackgroundColor(Color.WHITE);
    }
}
