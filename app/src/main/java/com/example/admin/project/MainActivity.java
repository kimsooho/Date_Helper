package com.example.admin.project;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btn, btnFoodChoice, btnMemo, btnMap;
    int year, month, day;
    int dyear, dmonth, dday;
    long counter;
    String sFood;
    String date = "";
    String startDay, nowDay;
    int FOODREQUESTCODE = 100;
    public static ArrayList<LatLng> arrayPoint = new ArrayList<>();
    public static ArrayList<String> arrayName = new ArrayList<>();
    public static ArrayList<String> arrayDate = new ArrayList<>();
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
        setTitle("D . H");

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        tvResult = (TextView)findViewById(R.id.tvResult);
        if(counter == 0){
            tvResult.setText("현재 날짜가 입력되지 않았습니다.");
        }else {
            tvResult.setText("사귄지 " + counter + "일");
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
        btnMemo = (Button)findViewById(R.id.btnMemo);
        btnMap = (Button)findViewById(R.id.btnMap);
        btnFoodChoice.setOnClickListener(this);
        btnMemo.setOnClickListener(this);
        btnMap.setOnClickListener(this);
        preferences();
    }

    @Override
    protected void onPause() {
        super.onPause();
        String[] latlong;
        SharedPreferences activity = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = activity.edit();
        editor.putString("date",startDay);
        editor.putInt("arraySize",arrayPoint.size());
        editor.putLong("cal",counter);

        for(int i=0; i<arrayPoint.size(); ++i){
            editor.putString("arrayName" + i,arrayName.get(i).toString());
            editor.putString("arrayDate" + i,arrayDate.get(i).toString());

            latlong = arrayPoint.get(i).toString().split(",");
            latlong[0] = latlong[0].replace("lat/lng: (","");
            latlong[1] = latlong[1].replace(")","");

            editor.putString("pointX" + i,latlong[0]);
            editor.putString("pointY" + i,latlong[1]);
        }

        editor.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnFoodChoice:
                Intent intent = new Intent(this, foodChoice.class);
                startActivityForResult(intent,FOODREQUESTCODE);
                break;
            case R.id.btnMemo:
                intent = new Intent(this, memo.class);
                startActivity(intent);
                break;
            case R.id.btnMap:

                intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("point",arrayPoint);
                intent.putExtra("name",arrayName);
                intent.putExtra("date",arrayDate);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == FOODREQUESTCODE && resultCode == Activity.RESULT_OK){
            sFood = data.getExtras().getString("food").toString();

            Toast.makeText(this,sFood,Toast.LENGTH_LONG).show();
        }else if(requestCode == 300 && resultCode == Activity.RESULT_OK){
            place = PlacePicker.getPlace(this, data);
            name = place.getName();
            address = place.getAddress();
            latlng = place.getLatLng();
            arrayPoint.add(latlng);
            arrayName.add(name.toString());
            //Toast.makeText(MainActivity.this, " 장소명 : " + name + "\n주소 : " + address + "\n좌표 : " + latlng,Toast.LENGTH_LONG).show();

            nowDate();
            arrayDate.add(date);
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
            case R.id.menuReset:
                reset();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //////////////////////////////////////////////// TIME 함수 구현부 ////////////////
    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            dyear = i; dmonth = i1+1; dday = i2;
            month++;
            if(dyear > year || ( dyear == year && dmonth > month) || (dyear == year && dmonth == month && dday > day)){
                Toast.makeText(MainActivity.this,"날짜를 다시 입력해주세요",Toast.LENGTH_LONG).show();
                --month;
                new DatePickerDialog(MainActivity.this, dateSetListener,year,month,day).show();
            }
            else {
                startDay = dyear+"-"+dmonth+"-"+dday;
                nowDay = year+"-"+month+"-"+day;

                try {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date startDate = format.parse(startDay);
                    Date nowDate = format.parse(nowDay);
                    counter = nowDate.getTime() - startDate.getTime();
                    counter /= (24*60*60*1000);
                    counter++;
                    tvResult.setText("함께    " + startDay + "\n" +
                            "오늘    " + nowDay + "\t\t\t\t\t\t\t\t\t\t" + counter + "일째");
                    --month;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        }
    };
    private void nowDate(){
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month++;
        date = year + "-" + month + "-" + day;
        month--;
    }
    private void preferences(){
        SharedPreferences activity = getPreferences(MODE_PRIVATE);
        int size = activity.getInt("arraySize",0);
        startDay = activity.getString("date","");
        counter = activity.getLong("cal",0);

        for(int i=0; i<size; ++i){ //*****************
            arrayName.add(activity.getString("arrayName" + i, null));
            arrayDate.add(activity.getString("arrayDate" + i, null));
            arrayPoint.add(new LatLng(Double.parseDouble(activity.getString("pointX" + i,"0")),
                    Double.parseDouble(activity.getString("pointY" + i, "0"))));
        }
        if(!(startDay.equals(""))){
            nowDate();
            tvResult.setText("함께    " + startDay + "\n" +
                    "오늘    " + date + "\t\t\t\t\t\t\t\t\t\t" + counter + "일째");

        }
    }
    private void reset(){
        SharedPreferences activity = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = activity.edit();
        int size = activity.getInt("arraySize",0);
        for(int i=0; i<size; ++i){
            editor.remove("arrayName"+i);
            editor.remove("arrayDate"+i);
            editor.remove("pointX"+i);
            editor.remove("pointY"+i);
        }
        arrayPoint.clear();
        arrayDate.clear();
        arrayName.clear();
        startDay = "";
        editor.remove("date");
        editor.remove("arraySize");
        tvResult.setText("현재 날짜가 입력되지 않았습니다.");

    }
}

