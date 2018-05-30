package com.example.admin.project;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

public class foodChoice extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    Adapter adapter;
    AutoScrollViewPager viewPager; // 슬라이드
    RadioGroup rg1, rg2, rg3;
    RadioButton rbKorea, rbChina, rbJapan, rbAmerica, rbMeat, rbChicken, rbFast, rbPizza, rbSnack, rbDessert, rbNoodle, rbOther;
    RadioButton[] radioButtons; // 랜덤을 위한 배열 선언
    Button btnRandom, btnFin;
    Boolean checked[] = {false, false, false}; // 라디오 그룹 3개를 컨트롤 하기 위함
    Button btn[] = new Button[9];
    Integer[] btnIDs = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8};
    String[] KoreaFood = {"냉면", "설렁탕", "닭볶음탕", "갈비탕", "김치찌개", "삼계탕", "백반", "국밥", "갈비찜"};
    String[] ChinaFood = {"탕수육", "깐풍기", "칠리새우", "짬뽕", "짜장면", "울면", "유린기", "야끼우동", "중화비빔밥"};
    String[] JapanFood = {"초밥", "돈부리", "소바", "회", "우동", "오코노미야끼", "라멘", "연어덮밥", "규카츠"};
    String[] AmericaFood = {"까르보나라", "로제파스타", "목살필라프", "크림리조또", "로제리조또", "빠네", "스프", "스테이크", "그라탕"};
    String[] Meat = {"삼겹살", "갈비", "갈매기살", "항정살", "목살", "가브리살", "등심", "스테이크", "육회"};
    String[] Chicken = {"꼬꼬아찌", "투존치킨", "BHC", "BBQ", "파닭", "땅땅치킨", "굽네치킨", "지코바", "맥시카나"};
    String[] Fast = {"맥도날드", "롯데리아", "맘스터치", "KFC", "버거킹", "벙커버거", "빅쉐프", "존스버거", "플라잉볼"};
    String[] Pizza = {"미스터피자", "피자헛", "도미노피자", "피자에땅", "59쌀피자", "뉴욕피자", "피자샵", "봉수아", "피자스쿨"};
    String[] Snack = {"신전떡볶이", "엽기떡볶이", "더 빨간떡볶이", "빨봉분식", "김밥천국", "김가네김밥", "레드스푼", "포장마차분식", "라볶이마을"};
    String[] Dessert = {"빙수", "마카롱", "몽블랑", "조각케이크", "밀푀유", "허니브레드", "도넛", "타르트", "아이스크림"};
    String[] Noodle = {"불닭볶음면", "비빔면", "짜파게티", "짜왕", "신라면", "진라면", "너구리", "진짬뽕", "라면 섞어먹기"};
    String[] Other = {"쌀국수", "카레", "돈가스", "곱창", "닭발", "아귀찜", "족발, 보쌈", "빵", "굶기"};
    String btnText;

    public foodChoice() {
        radioButtons = new RadioButton[]{rbKorea, rbChina, rbJapan, rbAmerica, rbMeat, rbChicken, rbFast, rbPizza, rbSnack, rbDessert, rbNoodle};
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_choice);
        setTitle("Food Choice");

        ArrayList<Integer> data = new ArrayList<>();
        data.add(R.drawable.food1);
        data.add(R.drawable.food2);
        data.add(R.drawable.food3);

        viewPager = (AutoScrollViewPager) findViewById(R.id.viewPager);
        adapter = new Adapter(this, data);
        viewPager.setAdapter(adapter);
        viewPager.setInterval(3000);
        viewPager.startAutoScroll();

        rg1 = (RadioGroup) findViewById(R.id.rg1);
        rg2 = (RadioGroup) findViewById(R.id.rg2);
        rg3 = (RadioGroup) findViewById(R.id.rg3);

        rg1.setOnCheckedChangeListener(this);
        rg2.setOnCheckedChangeListener(this);
        rg3.setOnCheckedChangeListener(this);


        btnRandom = (Button) findViewById(R.id.btnRandom);
        btnFin = (Button) findViewById(R.id.btnFin);
        btnRandom.setOnClickListener(this);
        btnFin.setOnClickListener(this);


        radioButtons = new RadioButton[]{rbKorea, rbChina, rbJapan, rbAmerica, rbMeat, rbChicken, rbFast, rbPizza, rbSnack, rbDessert, rbNoodle, rbOther};

        radioButtons[0] = (RadioButton) findViewById(R.id.rbKorea);
        radioButtons[1] = (RadioButton) findViewById(R.id.rbChina);
        radioButtons[2] = (RadioButton) findViewById(R.id.rbJapan);
        radioButtons[3] = (RadioButton) findViewById(R.id.rbAmerica);
        radioButtons[4] = (RadioButton) findViewById(R.id.rbMeat);
        radioButtons[5] = (RadioButton) findViewById(R.id.rbChicken);
        radioButtons[6] = (RadioButton) findViewById(R.id.rbFast);
        radioButtons[7] = (RadioButton) findViewById(R.id.rbPizza);
        radioButtons[8] = (RadioButton) findViewById(R.id.rbSnack);
        radioButtons[9] = (RadioButton) findViewById(R.id.rbDessert);
        radioButtons[10] = (RadioButton) findViewById(R.id.rbNoodle);
        radioButtons[11] = (RadioButton)findViewById(R.id.rbOther);

////////////////////////////////////////////////////////////////////////////////////////////
        for (int i = 0; i < btnIDs.length ; ++i) {
            btn[i] = (Button) findViewById(btnIDs[i]);
            btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < btnIDs.length; ++i) {
                        if (v.getId() == btn[i].getId()) {
                            btnText = btn[i].getText().toString();
                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(foodChoice.this);
                            alertDialogBuilder.setTitle("확인");

                            alertDialogBuilder.setMessage(btnText + "을(를) 선택 할까요?").setCancelable(false)
                                    .setPositiveButton("선택", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(foodChoice.this, MainActivity.class);
                                            intent.putExtra("food",btnText.toString());
                                            setResult(Activity.RESULT_OK, intent);
                                            finish();
                                        }
                                    })
                                    .setNegativeButton("다시", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                            alertDialogBuilder.show();
                        }
                    }


                }
            });
        }
        /////////////////////////////////////////////////
        InVisibleButton();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRandom:
                int random = (int) (Math.random() * 11);
                radioButtons[random].setChecked(true);
                random = (int) (Math.random() * 9);
                InVisibleButton();
                btn[random].setVisibility(View.VISIBLE);
                break;
            case R.id.btnFin:
                finish();
                break;
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        VisibleButton();
        if (group == rg1) {
            if (checked[1]) {
                rg2.clearCheck();
                checked[1] = false;
            } else if (checked[2]) {
                rg3.clearCheck();
                checked[2] = false;
            }
            checked[0] = true;

            if (checkedId == R.id.rbKorea) {
                ButtonSetText(KoreaFood);
            } else if (checkedId == R.id.rbAmerica) {
                ButtonSetText(AmericaFood);
            } else if (checkedId == R.id.rbFast) {
                ButtonSetText(Fast);
            } else if (checkedId == R.id.rbDessert) {
                ButtonSetText(Dessert);
            }
        } else if (group == rg2) {
            if (checked[0]) {
                rg1.clearCheck();
                checked[0] = false;
            } else if (checked[2]) {
                rg3.clearCheck();
                checked[2] = false;
            }
            checked[1] = true;

            if (checkedId == R.id.rbChina) {
                ButtonSetText(ChinaFood);
            } else if (checkedId == R.id.rbMeat) {
                ButtonSetText(Meat);
            } else if (checkedId == R.id.rbPizza) {
                ButtonSetText(Pizza);
            } else if (checkedId == R.id.rbNoodle) {
                ButtonSetText(Noodle);
            }
        } else if (group == rg3) {
            if (checked[0]) {
                rg1.clearCheck();
                checked[0] = false;
            } else if (checked[1]) {
                rg2.clearCheck();
                checked[1] = false;
            }
            checked[2] = true;

            if (checkedId == R.id.rbJapan) {
                ButtonSetText(JapanFood);
            } else if (checkedId == R.id.rbChicken) {
                ButtonSetText(Chicken);
            } else if (checkedId == R.id.rbSnack) {
                ButtonSetText(Snack);
            } else if (checkedId == R.id.rbOther) {
                ButtonSetText(Other);
            }
        }

    }


    ////////////////////////////////// 함수 구현부 /////////////////////////////////
    private void VisibleButton() {
        for (int i = 0; i < btnIDs.length; ++i) {
            btn[i].setVisibility(View.VISIBLE);
        }
    }

    private void InVisibleButton() {
        for (int i = 0; i < btnIDs.length; ++i) {
            btn[i].setVisibility(View.INVISIBLE);
        }
    }

    private void ButtonSetText(String[] str) {
        for (int i = 0; i < btnIDs.length ; ++i) {
            btn[i].setText(str[i]);
        }
    }
    //////////////////////////////// 함수 구현부 //////////////////////////////////////
}

