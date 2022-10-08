package com.zyq.permissiondemo;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.zyq.easypermission.EasyPermission;
import com.zyq.easypermission.EasyPermissionResult;

import java.util.List;

/**
 * 演示对DismissAsk
 * demo for DismissAsk
 *
 * @author Zhang YanQiang
 * @date 2019 06 03
 */
public class DismissAskActivity extends BaseActivity {
    TextView textView2;
    EasyPermission easyPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_reslut);
        textView2 = findViewById(R.id.textView2);

        findViewById(R.id.btnRequest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easyPermission.requestPermission();
            }
        });
        findViewById(R.id.btnShowResult).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOneActivity(ChekOnlyActivity.class);
            }
        });

        easyPermission = EasyPermission.build()
                .mRequestCode(RC_CODE_PERMISSION)
//                .mContext(DismissAskActivity.this)
                .mPerms(Manifest.permission.CAMERA)
                .mResult(new EasyPermissionResult() {
                    @Override
                    public void onPermissionsAccess(int requestCode) {
                        super.onPermissionsAccess(requestCode);
                        mToast("权限已通过");
                        //做你想做的
                        textView2.setText("权限已通过");
                    }

                    @Override
                    public void onPermissionsDismiss(int requestCode, @NonNull List<String> permissions) {
                        super.onPermissionsDismiss(requestCode, permissions);
                        mToast("权限被拒绝");
                        //你的权限被用户拒绝了你怎么办
                        textView2.setText(permissions.toString() + " 被拒绝了");
                    }

                    @Override
                    public boolean onDismissAsk(int requestCode, @NonNull List<String> permissions) {
                        mToast("权限被禁止");
                        //你的权限被用户禁止了并且不能请求了你怎么办
                        textView2.setText(permissions.toString() + " 被禁止了，需要向用户说明情况");
                        //弹出默认的权限详情设置提示弹出框，在设置页完成允许操作后，会自动回调到onPermissionsAccess()
                        easyPermission.openAppDetails(getString(R.string.permission_explain_camera));
                        //如果样式不满意，可以弹出自己的说明弹窗，在用户确认时调用easyPermission.goToAppSettings();完成跳转设置页
                        return true;//这里true表示拦截处理，不再回调onPermissionsDismiss；
                    }
                });
        easyPermission.requestPermission();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (easyPermission.getRequestCode() == requestCode) {
//            //设置界面返回
//            //Result from system setting
//            if (easyPermission.hasPermission(DismissAskActivity.this)) {
//                //做你想做的
//                textView2.setText("权限已通过");
//            } else {
//                //从设置回来还是没给你权限
//                textView2.setText("从设置回来还是没打开权限");
//            }
//
//        }
//    }
}
