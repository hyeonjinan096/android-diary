package com.example.yourgrowth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class CalenderFragment_Activity extends AppCompatActivity {

    public String fname=null;
    public String str=null;
    public CalendarView calendarView;
    public Button cha_Btn,del_Btn,save_Btn;
    public TextView diaryTextView,textView2,textView3;
    public EditText contextEditText;
    public LinearLayout mood;

    //무드 변경하는 코드
    int score;
    public ImageView moodshow;
    ImageView s1,s2,s3,s4,s5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_calender);
        calendarView=findViewById(R.id.calendarView);
        diaryTextView=findViewById(R.id.diaryTextView);


        moodshow=findViewById(R.id.moodshow);
        s3=findViewById(R.id.stamp_3);
        s4=findViewById(R.id.stamp_4);
        s5=findViewById(R.id.stamp_5);
        s1=findViewById(R.id.stamp_1);
        s2=findViewById(R.id.stamp_2);

        s3.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {moodcheck(3); }});
        s4.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {moodcheck(4); }});
        s5.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {moodcheck(5); }});
        s2.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {moodcheck(2); }});
        s1.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"확인", Toast.LENGTH_LONG).show();

                moodcheck(1); }});




        mood = findViewById(R.id.mood);
        save_Btn=findViewById(R.id.save_Btn);
        del_Btn=findViewById(R.id.del_Btn);
        cha_Btn=findViewById(R.id.cha_Btn);
        textView2=findViewById(R.id.textView2);
        textView3=findViewById(R.id.textView3);
        contextEditText=findViewById(R.id.contextEditText);
        //로그인 및 회원가입 엑티비티에서 이름을 받아옴
        Intent intent=getIntent();
        String name=intent.getStringExtra("userName");
        final String userID=intent.getStringExtra("userID");
        textView3.setText(name+"님의 달력 일기장");

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                diaryTextView.setVisibility(View.VISIBLE);

                mood.setVisibility(View.VISIBLE);
                save_Btn.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.INVISIBLE);
                moodshow.setVisibility(View.INVISIBLE);
                cha_Btn.setVisibility(View.INVISIBLE);
                del_Btn.setVisibility(View.INVISIBLE);
                diaryTextView.setText(String.format("%d / %d / %d",year,month+1,dayOfMonth));
                contextEditText.setText("");
                checkDay(year,month,dayOfMonth,userID);
                //날짜를 보면 수행되는 코드
            }
        });
        save_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDiary(fname);//해당 날짜 내용 저장 함수
                str=contextEditText.getText().toString();
                textView2.setText(str);
                mood.setVisibility(View.INVISIBLE);
                save_Btn.setVisibility(View.INVISIBLE);
                cha_Btn.setVisibility(View.VISIBLE);
                del_Btn.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.INVISIBLE);
                textView2.setVisibility(View.VISIBLE);
                moodshow.setVisibility(View.VISIBLE);

            }
        });
    }



    public void moodcheck(int n){
        score=n;
        switch (score) {	// 조건

            case 1:			// 값 불일치(미실행)
                moodshow.setImageResource(R.drawable.stamp_1);
                System.out.println("확인1");

                Toast.makeText(getApplicationContext(),"확인2", Toast.LENGTH_LONG).show();
                break;
            case 2:			// 값 일치
                moodshow.setImageResource(R.drawable.stamp_2);
                break;
            case 3:
                moodshow.setImageResource(R.drawable.stamp_3);
                break;
            case 4:
                moodshow.setImageResource(R.drawable.stamp_4);
                break;
            case 5:
                moodshow.setImageResource(R.drawable.stamp_5);
                break;
            default: Toast.makeText(getApplicationContext(),",", Toast.LENGTH_LONG).show();

        }

    }


    public void  checkDay(int cYear,int cMonth,int cDay,String userID){
        fname=""+userID+cYear+"-"+(cMonth+1)+""+"-"+cDay+".txt";//저장할 파일 이름설정
        FileInputStream fis=null;//FileStream fis 변수

        try{
            fis=openFileInput(fname);   //fname이름으로 저장소 열기

            byte[] fileData=new byte[fis.available()];
            fis.read(fileData);   //저장소에 데이터 읽기  ?
            fis.close();           //저장소 닫기  ?

            str=new String(fileData);   //1파일에 있는 내용

            contextEditText.setVisibility(View.INVISIBLE);
            textView2.setVisibility(View.VISIBLE);
            moodshow.setVisibility(View.VISIBLE);

            textView2.setText(str);    //1여기에 세팅

            mood.setVisibility(View.INVISIBLE);
            save_Btn.setVisibility(View.INVISIBLE);
            cha_Btn.setVisibility(View.VISIBLE);
            del_Btn.setVisibility(View.VISIBLE);//수정,삭제 visible

            cha_Btn.setOnClickListener(new View.OnClickListener() {   //수정버튼 눌렀으면
                @Override
                public void onClick(View view) {
                    contextEditText.setVisibility(View.VISIBLE);
                    textView2.setVisibility(View.INVISIBLE);
                    moodshow.setVisibility(View.INVISIBLE);
                    contextEditText.setText(str);

                    mood.setVisibility(View.VISIBLE);
                    save_Btn.setVisibility(View.VISIBLE);
                    cha_Btn.setVisibility(View.INVISIBLE);
                    del_Btn.setVisibility(View.INVISIBLE);
                    textView2.setText(contextEditText.getText());     //텍스트 다시 불러오기?
                }

            });
            del_Btn.setOnClickListener(new View.OnClickListener() {    //삭제버튼 눌렀으면
                @Override
                public void onClick(View view) {
                    textView2.setVisibility(View.INVISIBLE);
                    moodshow.setVisibility(View.INVISIBLE);
                    contextEditText.setText("");
                    mood.setVisibility(View.INVISIBLE);
                    contextEditText.setVisibility(View.VISIBLE);

                    mood.setVisibility(View.VISIBLE);
                    save_Btn.setVisibility(View.VISIBLE);
                    cha_Btn.setVisibility(View.INVISIBLE);
                    del_Btn.setVisibility(View.INVISIBLE);
                    removeDiary(fname);   //해당 저장소 내용 삭제 함수
                }
            });
            if(textView2.getText()==null){
                moodshow.setVisibility(View.INVISIBLE);
                textView2.setVisibility(View.INVISIBLE);
                diaryTextView.setVisibility(View.VISIBLE);
                mood.setVisibility(View.INVISIBLE);

                mood.setVisibility(View.VISIBLE);
                save_Btn.setVisibility(View.VISIBLE);
                cha_Btn.setVisibility(View.INVISIBLE);
                del_Btn.setVisibility(View.INVISIBLE);
                contextEditText.setVisibility(View.VISIBLE);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @SuppressLint("WrongConstant")
    public void removeDiary(String readDay){//해당 날짜 내용 삭제 함수
        FileOutputStream fos=null;

        try{
            fos=openFileOutput(readDay,MODE_NO_LOCALIZED_COLLATORS);
            String content="";
            fos.write((content).getBytes());//비우기
            fos.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @SuppressLint("WrongConstant")
    public void saveDiary(String readDay){//해당 날짜 내용 저장 함수
        FileOutputStream fos=null;

        try{
            fos=openFileOutput(readDay,MODE_NO_LOCALIZED_COLLATORS);
            String content=contextEditText.getText().toString();
            fos.write((content).getBytes());//채우기
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}