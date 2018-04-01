package com.efrei.signin;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.utils.TimeUtils;
import com.gc.materialdesign.views.ButtonRectangle;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.efrei.api.Api;
import com.efrei.app.App;
import com.efrei.bean.ResponseLogin;
import com.efrei.utils.Constant;
import com.efrei.utils.JsonUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import io.paperdb.Paper;
import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    private ButtonRectangle btn_StartAm;
    private ButtonRectangle btn_EndAm;
    private ButtonRectangle btn_StartPm;
    private ButtonRectangle btn_EndPm;
    private ButtonRectangle btn_StartN;
    private ButtonRectangle btn_EndN;

    private FloatingActionButton actionA;
    private FloatingActionButton actionB;
    private FloatingActionButton actionC;

    private ResponseLogin login;
    private MaterialDialog md;
    private String str_mac;
    private TextView tv_signin;
    private Toolbar mainToolbar;

    private boolean quit = false; //设置退出标识

    @Override
    protected void attachBaseContext(Context newbase){
        super.attachBaseContext(LocaleHelper.onAttach(newbase,"en"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Paper.init(this);
        String language = Paper.book().read("language");
        if(language == null)
            Paper.book().write("language","en");*/
        initView();
        initEvent();
        //updateView ((String)Paper.book().read("language"));
    }

    /*private void updateView(String lang){
        Context context = LocaleHelper.setLocale(this,lang);
        Resources resources = context.getResources();

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.action_EN)
        {
            Paper.book().write("language","en");
            updateView((String)Paper.book().read("language"));
            //Log.v("EN", String.valueOf(item));
        }
        else if (item.getItemId() == R.id.action_FR)
        {
            Paper.book().write("language","fr");
            updateView((String)Paper.book().read("language"));
            //Log.v("FR", String.valueOf(item));
        }
        else if (item.getItemId() == R.id.action_ZH)
        {
            Paper.book().write("language","zh");
            updateView((String)Paper.book().read("language"));
            //Log.v("ZH", String.valueOf(item));
        }
        return true;
    }*/

    private void initView(){
        btn_StartAm=(ButtonRectangle)findViewById(R.id.btn_StartAm);
        btn_EndAm=(ButtonRectangle)findViewById(R.id.btn_EndAm);
        btn_StartPm=(ButtonRectangle)findViewById(R.id.btn_StartPm);
        btn_EndPm=(ButtonRectangle)findViewById(R.id.btn_EndPm);
        btn_StartN=(ButtonRectangle)findViewById(R.id.btn_StartN);
        btn_EndN=(ButtonRectangle)findViewById(R.id.btn_EndN);
        actionA = (FloatingActionButton) findViewById(R.id.action_a);
        actionB = (FloatingActionButton) findViewById(R.id.action_b);
        actionC = (FloatingActionButton) findViewById(R.id.action_c);
        tv_signin=(TextView)findViewById(R.id.tv_signin);
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(" yyyy-MM-dd  EEE");
        String str_Date=TimeUtils.getCurTimeString(sdf);
        tv_signin.setText(getResources().getString(R.string.main_date_message)+str_Date);
        mainToolbar=(Toolbar)findViewById(R.id.mainToolbar);

        mainToolbar.inflateMenu(R.menu.menu_main);
        mainToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int id=item.getItemId();
                if(id==R.id.action_settings){
                    toActivity(SettingActivity.class);
                }
                if(id==R.id.action_about){
                    toActivity(AboutActivity.class);
                }
                return false;
            }
        });
    }
    private void initEvent(){
        /*第一个悬浮菜单点击事件*/
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toActivity(HolidayActivity.class);
            }
        });
        actionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toActivity(HolidayRecordActivity.class);
            }
        });
        actionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toActivity(SignInRecordActivity.class);
            }
        });



        btn_StartAm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SignIn(Constant.AM_SIGNIN);
            }
        });

        btn_EndAm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SignIn(Constant.AM_SIGNOUT);
            }
        });

        btn_StartPm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SignIn(Constant.PM_SIGNIN);
            }
        });
        btn_EndPm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SignIn(Constant.PM_SIGNOUT);
            }
        });
        btn_StartN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SignIn(Constant.NIGHT_SIGNIN);
            }
        });
        btn_EndN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SignIn(Constant.NIGHT_SIGNOUT);
            }
        });
    }

    /*签到提示对话框*/
    public void signSuccess(String msg){
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(getResources().getString(R.string.main_prompt_message))
                .content(msg)
                .positiveText(getResources().getString(R.string.main_submit_message))
                .show();
    }


    /*页面跳转*/
    public void toActivity(Class<?> clazz){
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
    }
    /*显示正在加载对话框*/
    public void showWaitDialog(){
         /*显示提示正在签到对话框*/
        md=new MaterialDialog.Builder(this)
                .title(getResources().getString(R.string.main_prompt_message))
                .content(getResources().getString(R.string.main_signing_message))
                .progress(true, 0)
                .show();
    }

    public void SignIn(String type){
        showWaitDialog();
        /*获取当前时间*/
        java.text.SimpleDateFormat ss = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
        /*主要是为了拼凑unique_id*/
        java.text.SimpleDateFormat ss2 = new java.text.SimpleDateFormat("yyyyMMdd");
        String date= TimeUtils.getCurTimeString(ss);
        String date2= TimeUtils.getCurTimeString(ss2);

        /*获得当前登录用户的用户名*/
        String user_name=App.getInstance().getUser(MainActivity.this);
         /*没人每天只有一个id*/
        String unique_id=user_name+date2;

        /*构造请求体*/
        HashMap<String, String> params = new HashMap<>();
        params.put("unique_id", unique_id);
        params.put("type", type);
        params.put("date", date);
        params.put("user_name",user_name);
        JSONObject jsonObject = new JSONObject(params);
                /*发送登录请求*/
        OkGo.post(Api.SIGNIN)//
                .tag(this)//
                .upJson(jsonObject.toString())//
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        Log.e( getResources().getString(R.string.main_main_message), response.code()+ getResources().getString(R.string.main_code_message)+call.toString() );
                        if(response.code()!=200){
                             /*打开签到成功提示对话框*/
                            signSuccess(getResources().getString(R.string.main_maintain_message));
                            return;
                        }
                        login=new ResponseLogin();
                        login= JsonUtils.fromJson(s,ResponseLogin.class);
                        if(login.getStatus().equals(Constant.SUCCESS)){
                            /*关闭加载对话框*/
                            md.dismiss();
                            /*打开签到成功提示对话框*/
                            signSuccess( getResources().getString(R.string.main_success_message));
                        }else if(login.getMsg().equals(Constant.ERROR_SYSTEM)){
                             /*关闭加载对话框*/
                            md.dismiss();
                            /*打开签到成功提示对话框*/
                            signSuccess( getResources().getString(R.string.main_wrong_message));
                        }else if(login.getMsg().equals(Constant.ERROR_ALREADY_SIGNIN)){
                             /*关闭加载对话框*/
                            md.dismiss();
                            /*打开签到成功提示对话框*/
                            signSuccess( getResources().getString(R.string.main_double_message));
                        }
                    }
                });
    }
    /*双击退出程序*/
    @Override
    public void onBackPressed(){
        if(quit==false){//询问是否退出程序
            Toast.makeText(MainActivity.this, getResources().getString(R.string.main_exit_message),Toast.LENGTH_SHORT).show();
            new Timer(true).schedule(new TimerTask() {
                @Override
                public void run() {
                    quit=false;
                }
            },2000);
            quit=true;
        }else {
            super.onBackPressed();
            finish();
        }
    }

}
