package com.duapps.f.proxydemo;

public class InterfaceImpl implements IInterface {
    @Override
    public String getResult(String oriStr) {
        return "OriStr:" + oriStr;
    }
}
