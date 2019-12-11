package com.phong.baitaptugiaimenu_3;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.phong.adapter.LienLacAdapter;
import com.phong.model.LienLac;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvNhaHang;
    LienLacAdapter lienLacAdapter;
    LienLac chonLienLac = null;
    ArrayList<LienLac> dsNguon = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
        fakeData();
    }

    private void addEvents() {
        //lấy contact được chọn trước đó trong ListView
        //Vì khi mở context menu sẽ làm mất focus nên ta phải lưu lại trước
        //khi mở context menu
        lvNhaHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //lưu vết contact được chọn trong ListView
                chonLienLac = (LienLac) lienLacAdapter.getItem(i);
                return false;
            }
        });
    }

    private void fakeData() {
        lienLacAdapter.add(new LienLac(R.drawable.hinh1, "Nhà hàng Thanh", "09547128543"));
        lienLacAdapter.add(new LienLac(R.drawable.hinh2, "Nhà hàng Phong", "0314587268"));
        lienLacAdapter.add(new LienLac(R.drawable.hinh3, "Nhà hàng Mạnh", "0947153298"));
        lienLacAdapter.add(new LienLac(R.drawable.hinh4, "Nhà hàng Nguyên", "0397156824"));
        lienLacAdapter.add(new LienLac(R.drawable.hinh5, "Nhà hàng Tuyết", "0971462583"));
        lienLacAdapter.add(new LienLac(R.drawable.hinh6, "Nhà hàng Duy", "0326594871"));
        lienLacAdapter.add(new LienLac(R.drawable.hinh7, "Nhà hàng Thuỷ", "0301054867"));
        lienLacAdapter.add(new LienLac(R.drawable.hinh8, "Nhà hàng Hiếu", "0541987305"));
        lienLacAdapter.add(new LienLac(R.drawable.hinh9, "Nhà hàng Cường", "0378562419"));
        lienLacAdapter.add(new LienLac(R.drawable.hinh10, "Nhà hàng Anh", "0385204761"));
    }

    private void addControls() {
        lvNhaHang = (ListView) findViewById(R.id.lvNhaHang);
        lienLacAdapter = new LienLacAdapter(MainActivity.this, R.layout.item);
        lvNhaHang.setAdapter(lienLacAdapter);

        registerForContextMenu(lvNhaHang);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        MenuItem menuTim = menu.findItem(R.id.menuTim);
        SearchView searchView = (SearchView) menuTim.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.isEmpty())
                {
                    lienLacAdapter.clear();
                    lienLacAdapter.addAll(dsNguon);
                }
                else {
                    ArrayList<LienLac> dsTim = new ArrayList<LienLac>();
                    for (LienLac lienLac : dsNguon) {
                        if (lienLac.getTen().contains(s) || lienLac.getSoDienThoai().contains(s)) {
                            dsTim.add(lienLac);
                        }
                    }
                    lienLacAdapter.clear();
                    lienLacAdapter.addAll(dsTim);
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
        menu.setHeaderTitle("Bản đồ - Gọi - Nhắn tin");
        menu.getItem(1).setTitle("Gọi tới " + chonLienLac.getSoDienThoai());
        menu.getItem(2).setTitle("Nhắn tin tới " + chonLienLac.getSoDienThoai());
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuGoi:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    xuLyGoiDien();
                }
                break;
            case R.id.menuNhanTin:
                xuLyNhanTin();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void xuLyNhanTin() {
        Intent intent = new Intent(MainActivity.this, SmsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("LIENLAC", chonLienLac);
        intent.putExtra("DULIEU", bundle);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void xuLyGoiDien() {
        Uri uri = Uri.parse("Gọi:" + chonLienLac.getSoDienThoai());
        Intent intent = new Intent(Intent.ACTION_CALL, uri);
        intent.setData(uri);
        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }
}
