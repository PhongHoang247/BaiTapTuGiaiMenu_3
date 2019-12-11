package com.phong.model;

import java.io.Serializable;

public class LienLac implements Serializable {
    protected int hinh;
    protected String ten;
    protected String soDienThoai;

    public LienLac() {
    }

    public LienLac(int hinh, String ten, String soDienThoai) {
        this.hinh = hinh;
        this.ten = ten;
        this.soDienThoai = soDienThoai;
    }

    public int getHinh() {
        return hinh;
    }

    public void setHinh(int hinh) {
        this.hinh = hinh;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
}
