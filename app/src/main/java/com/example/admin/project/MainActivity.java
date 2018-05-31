package com.example.admin.project;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btn, btnFoodChoice, btnHistory, btnMap;
    int year, month, day;
    int dyear, dmonth, dday;
    long calDate=0;
    String sFood="1";
    int FOODREQUESTCODE = 100;
    public static ArrayList<LatLng> arrayPoint = new ArrayList<>();
    public static ArrayList<String> arrayName = new ArrayList<>();
    Place place;
    CharSequence name;
    CharSequence address;
    LatLng latlng;
    TextView tvResult;
    GregorianCalendar calendar = new GregorianCalendar();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        tvResult = (TextView)findViewById(R.id.tvResult);
        //tvResult.setText("오늘 먹은 음식 : ");
        if(calDate == 0){
            tvResult.setText("현재 날짜가 입력되지 않았습니다.");
        }else {
            tvResult.setText("사귄지 " + calDate + "일");
        }
        btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
                try {
                    Intent intent = intentBuilder.build(MainActivity.this);
                    startActivityForResult(intent, 300);
                }
                catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        });

        btnFoodChoice = (Button)findViewById(R.id.btnFoodChoice);
        btnHistory = (Button)findViewById(R.id.btnHistory);
        btnMap = (Button)findViewById(R.id.btnMap);
        btnFoodChoice.setOnClickListener(this);
        btnHistory.setOnClickListener(this);
        btnMap.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnFoodChoice:
                Intent intent = new Intent(this, foodChoice.class);
                startActivityForResult(intent,FOODREQUESTCODE);
                break;
            case R.id.btnHistory:
                Toast.makeText(this,sFood,Toast.LENGTH_LONG).show();
                break;
            case R.id.btnMap:
                intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("point",arrayPoint);
                intent.putExtra("name",arrayName);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == FOODREQUESTCODE && resultCode == Activity.RESULT_OK){
            sFood = data.getExtras().getString("food").toString();
            tvResult.append(sFood + ",");

            //tvResult.setText(tvResult.getText().toString().substring(tvResult.length()-1));
            Toast.makeText(this,sFood,Toast.LENGTH_LONG).show();
        }else if(requestCode == 300 && resultCode == Activity.RESULT_OK){
            place = PlacePicker.getPlace(this, data);
            name = place.getName();
            address = place.getAddress();
            latlng = place.getLatLng();
            arrayPoint.add(latlng);
            arrayName.add(name.toString());
            Toast.makeText(MainActivity.this, " 장소명 : " + name + "\n주소 : " + address + "\n좌표 : " + latlng,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuDate:
                new DatePickerDialog(this, dateSetListener,year,month,day).show();
                break;
            case R.id.menuInfo:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("\t\t\t\t\tInformation").setMessage("Date Helper \n\n" +
                        "제작 5293348 김수호\n\n" +
                        "v 1.0\n").show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //////////////////////////////////////////////// TIME 함수 구현부 ////////////////
    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            dyear = i; dmonth = i1+1; dday = i2;
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            month++;
            day = calendar.get(Calendar.DAY_OF_MONTH);
            if(year < dyear || month < dmonth || day < dday){
                Toast.makeText(MainActivity.this,"날짜를 다시 입력해주세요",Toast.LENGTH_LONG).show();
                --month;
                new DatePickerDialog(MainActivity.this, dateSetListener,year,month,day).show();
            }
            else {
                String date1 = year + "-" + month + "-" + day;
                String date2 = dyear + "-" + dmonth + "-" + dday;
                try {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
                    Date firstDate = format.parse(date1);
                    Date secondDate = format.parse(date2);
                    calDate = firstDate.getTime() - secondDate.getTime();
                    calDate = calDate / (24 * 60 * 60 * 1000);
                    calDate++;
                    tvResult.setText("함께    " + date1 + "\n" +
                            "오늘    " + date2 + "\t\t\t\t\t\t\t\t\t\t" + calDate + "일째");

                    --month;

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        }
    };
}
