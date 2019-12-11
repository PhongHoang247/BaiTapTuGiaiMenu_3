package com.phong.baitaptugiaimenu_3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.phong.model.LienLac;

public class SmsActivity extends AppCompatActivity {

    TextView txtGui;
    EditText edtTinNhan;
    Button btnGui;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        addControl();

        //Lấy thông tin từ Intent
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("DULIEU");
        final LienLac lienLac = (LienLac) bundle.getSerializable("LIENLAC");
        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guiTinNhan(lienLac);
            }
        });
        txtGui.setText("Gửi tới:" + lienLac.getSoDienThoai());
    }

    /**
     * hàm dùng để gửi tin nhắn có kiểm tra kết quả trả về
     * Tôi chưa giải thích ở đây được vì nó liên quan rất nhiều
     * kiến thức, khi nào tới Broadcast Receiver, telephony Tôi sẽ
     * giải thích lại
     * @param lienLac
     */
    private void guiTinNhan(LienLac lienLac) {
        //Lấy mặc định SmsManager
        SmsManager sms = SmsManager.getDefault();
        Intent msgGui = new Intent("GUI_TIN_NHAN");
        //Khai báo pendingintent để kiểm tra kết quả
        PendingIntent pendingMsgGui = PendingIntent.getBroadcast(this,0,msgGui, 0);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int ketQua = getResultCode();
                String msg = "Gửi OK";
                if (ketQua != Activity.RESULT_OK)
                {
                    msg = "Gửi thất bại";
                }
                Toast.makeText(SmsActivity.this,msg,Toast.LENGTH_SHORT).show();
            }
        }, new IntentFilter("GUI_TIN_NHAN"));
        //Gọi hàm gửi tin nhắn đi
        sms.sendTextMessage(lienLac.getSoDienThoai(),null,edtTinNhan.getText() + "",
                pendingMsgGui,null);
        finish();
    }

    private void addControl() {
        txtGui = findViewById(R.id.txtGui);
        edtTinNhan = findViewById(R.id.edtTinNhan);
        btnGui = findViewById(R.id.btnGuiTinNhan);
    }
}
