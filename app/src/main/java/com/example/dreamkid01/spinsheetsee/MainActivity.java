package com.example.dreamkid01.spinsheetsee;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    Context context;

    Handler handler = new Handler();

    String userId;

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @SuppressLint("ResourceType")
    public void onClick(View view) {
        View inflatedView;
        switch (view.getId())
        {
            case R.id.login:
                EditText editId = (EditText) findViewById(R.id.editId);
                EditText editPassword = (EditText) findViewById(R.id.editPassword);

                userId = editId.getText().toString();
                loadHtml(1, "http://spinsheet.dothome.co.kr/login.php?userid=" + editId.getText().toString() + "&userpass=" + editPassword.getText().toString());
                break;
            case R.id.signin:
                inflatedView = LayoutInflater.from(MainActivity.this).inflate(R.layout.register, null);
                setContentView(inflatedView);
                break;
            case R.id.btnRegister:
                EditText editRegisterId = (EditText) findViewById(R.id.editRegister);
                EditText editRegisterPassword = (EditText) findViewById(R.id.editRegisterPassword);
                CheckBox checkRegisterPcm = (CheckBox) findViewById(R.id.checkRegisterPcm);
                CheckBox checkRegisterProgram = (CheckBox) findViewById(R.id.checkRegisterProgram);

                int pcm = checkRegisterPcm.isChecked() ? 1 : 0;
                int program = checkRegisterProgram.isChecked() ? 1 : 0;

                Log.d("kisa", "http://spinsheet.dothome.co.kr/signup.php?userid=" + editRegisterId.getText().toString() + "&userpass=" + editRegisterPassword.getText().toString() + "&pcm=" + pcm + "&program=" + program);
                loadHtml(2, "http://spinsheet.dothome.co.kr/signup.php?userid=" + editRegisterId.getText().toString() + "&userpass=" + editRegisterPassword.getText().toString() + "&pcm=" + pcm + "&program=" + program);
//                setContentView(R.layout.activity_main);
//                Toast.makeText(MainActivity.this, "회원가입 완료", Toast.LENGTH_SHORT).show();
                break;
            case R.id.registNO:
                setContentView(R.layout.activity_main);
                break;
            case R.id.sendPaPer:
                inflatedView = LayoutInflater.from(MainActivity.this).inflate(R.layout.sendpap, null);
                setContentView(inflatedView);
                break;
            case R.id.q_goback:
                inflatedView = LayoutInflater.from(MainActivity.this).inflate(R.layout.menu, null);
                setContentView(inflatedView);
                break;
            case R.id.btnSendPaper:
                EditText editTitle = (EditText) findViewById(R.id.editSendTitle);
                EditText editContent = (EditText) findViewById(R.id.editSendContent);

                CheckBox checkProgram = (CheckBox) findViewById(R.id.checkSendProgram);
                CheckBox checkPcm = (CheckBox) findViewById(R.id.checkSendPcm);

                int sendPcm = checkPcm.isChecked() == true ? 1 : 0;
                int sendProgram = checkProgram.isChecked() == true ? 1 : 0;


                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioSend);
                radioGroup.getCheckedRadioButtonId();

                Log.d("kisa", "http://spinsheet.dothome.co.kr/send_paper.php?userid=" + userId + "&title=" + editTitle.getText().toString() + "&content=" + editContent.getText().toString() + "&pcm=" + sendPcm + "&program=" + sendProgram);
                loadHtml(3, "http://spinsheet.dothome.co.kr/send_paper.php?userid=" + userId + "&title=" + editTitle.getText().toString() + "&content=" + editContent.getText().toString() + "&pcm=" + sendPcm + "&program=" + sendProgram);
                break;

            case R.id.getPaPer:
                inflatedView = LayoutInflater.from(MainActivity.this).inflate(R.layout.getpap, null);
                setContentView(inflatedView);
                break;

            case R.id.getans:
                break;

            case R.id.ansBtn:
                break;

            case R.id.a_goback:
                inflatedView = LayoutInflater.from(MainActivity.this).inflate(R.layout.menu, null);
                setContentView(inflatedView);
                break;
            case R.id.myPaper:
                inflatedView = LayoutInflater.from(MainActivity.this).inflate(R.layout.mypap, null);
                setContentView(inflatedView);

                mListView = (ListView)findViewById(R.id.listMyPaper);
                break;
            case R.id.getmyans:

                break;
            case R.id.m_goback:
                inflatedView = LayoutInflater.from(MainActivity.this).inflate(R.layout.menu, null);
                setContentView(inflatedView);
                break;
            case R.id.btnRecommendOption:
                inflatedView = LayoutInflater.from(MainActivity.this).inflate(R.layout.recpap, null);
                setContentView(inflatedView);
                break;
            case R.id.btnSelectPcm:
                loadHtml(4, "http://spinsheet.dothome.co.kr/recommend?pcm=1&program=0");

                break;
            case R.id.btnSelectProgram:
                loadHtml(4, "http://spinsheet.dothome.co.kr/recommend?pcm=0&program=1");

                break;
            case R.id.r_goback:
                inflatedView = LayoutInflater.from(MainActivity.this).inflate(R.layout.menu, null);
                setContentView(inflatedView);
                break;

        }
    }

    void loadHtml(final int type, final String urlAddress) { // 웹에서 html 읽어오기
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuffer sb = new StringBuffer();

                try {
                    URL url = new URL(urlAddress);
                    HttpURLConnection conn =
                            (HttpURLConnection)url.openConnection();// 접속
                    if (conn != null) {
                        conn.setConnectTimeout(2000);
                        conn.setUseCaches(false);
                        if (conn.getResponseCode()
                                ==HttpURLConnection.HTTP_OK){
                            //    데이터 읽기
                            BufferedReader br
                                    = new BufferedReader(new InputStreamReader
                                    (conn.getInputStream(),"euc-kr"));//"utf-8"
                            while(true) {
                                String line = br.readLine();
                                if (line == null) break;
                                sb.append(line+"\n");
                            }
                            br.close(); // 스트림 해제
                        }
                        conn.disconnect(); // 연결 끊기
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
//                            Toast.makeText(MainActivity.this, "로그입니다", Toast.LENGTH_SHORT).show();

                            String web = sb.toString();
                            Log.d("kisa", "LOG: " + type);

                            View inflatedView;
                            switch(type)
                            {
                                case 1:
//                                    Log.d("kisa", String.valueOf(web.indexOf("result:1")));
                                    if(web.indexOf("result:1") != -1)
                                    {
                                        setContentView(LayoutInflater.from(MainActivity.this).inflate(R.layout.menu, null));

                                        Toast.makeText(MainActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                        Toast.makeText(MainActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                                    break;

                                case 2:
                                    setContentView(LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_main, null));
                                    Toast.makeText(MainActivity.this, "회원가입을 완료하였습니다!", Toast.LENGTH_SHORT).show();
                                    break;

                                case 3:
                                    setContentView(LayoutInflater.from(MainActivity.this).inflate(R.layout.menu, null));
                                    Toast.makeText(MainActivity.this, "글을 등록하였습니다!", Toast.LENGTH_SHORT).show();
                                    break;

                                case 4:
                                    int pos1 = web.indexOf("<title>") + "<title>".length();
                                    int pos2 = web.indexOf("</title>");

//                                    Log.d("kisa", web + " - " + pos1 + " / " + pos2);

                                    String recTitle = web.substring(pos1, pos2);

                                    pos1 = web.indexOf("<content>") + "<content>".length();
                                    pos2 = web.indexOf("</content>");

//                                    Log.d("kisa", web + " - " + pos1 + " / " + pos2);

                                    String recContent = web.substring(pos1, pos2);

                                    Log.d("kisa", "Title: " + recTitle + " / Content: " + recContent);

                                    setContentView(LayoutInflater.from(MainActivity.this).inflate(R.layout.recscreen, null));

                                    TextView textRecTitle = (TextView) findViewById(R.id.textRecTitle);
                                    TextView textRecContent = (TextView) findViewById(R.id.textRecContent);
//                                    TextView textRecTitle = (TextView) findViewById(R.id.textRecTitle);

                                    textRecTitle.setText(recTitle);
                                    textRecContent.setText(recContent);
                                    break;

                                default:
                                    Log.d("kisa", "I DON'T KNOW");
                                    break;
                            }
                        }
                    });

//                    View inflatedView;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.start(); // 쓰레드 시작
    }

    public void ShowView ()
    {
    }
}
