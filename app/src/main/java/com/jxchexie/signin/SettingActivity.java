package com.jxchexie.signin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.utils.NetworkUtils;
import com.blankj.utilcode.utils.StringUtils;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.views.CheckBox;
import com.jxchexie.api.Api;
import com.jxchexie.app.App;
import com.jxchexie.bean.ResponseLogin;
import com.jxchexie.utils.Constant;
import com.jxchexie.utils.JsonUtils;
import com.jxchexie.utils.PreferencesUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

/*设置页面*/
public class SettingActivity extends AppCompatActivity {

    private ButtonRectangle btn_exit;
    private LinearLayout linear_check_version;
    private Toolbar settingsToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        initEvent();
    }

    private void initView(){
        btn_exit=(ButtonRectangle)findViewById(R.id.btn_exit);
        linear_check_version=(LinearLayout) findViewById(R.id.linear_check_version);
        settingsToolbar=(Toolbar) findViewById(R.id.settingsToolbar);
        settingsToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.this.finish();
            }
        });
    }
    private void initEvent(){
        /*退出当前登录*/
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(SettingActivity.this)
                        .title(getResources().getString(R.string.setting_prompt_message))
                        .content(getResources().getString(R.string.setting_exit_message))
                        .positiveText(getResources().getString(R.string.setting_yes_message))
                        .negativeText(getResources().getString(R.string.setting_cancel_message))
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                // 确定退出,移除sp中的用户名密码
                                App.getInstance().removeUser(SettingActivity.this);
                                toActivity(LoginActivity.class);
                                SettingActivity.this.finish();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();

            }
        });

        linear_check_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage( getResources().getString(R.string.setting_noUpdate_message));
            }
        });
    }


    /*页面跳转*/
    public void toActivity(Class<?> clazz){
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
    }

    /*签到提示对话框*/
    public void showMessage(String msg){
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title( getResources().getString(R.string.setting_prompt_message))
                .content(msg)
                .positiveText(getResources().getString(R.string.setting_yes_message))
                .show();
    }
}
