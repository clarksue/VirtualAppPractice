package com.duapps.f.proxydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.duapps.f.demo1.R;

import java.lang.reflect.Proxy;

public class ProxyDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy_demo);
        IInterface iInterface = new InterfaceImpl();

        IInterface iInterfaceProxyObj = (IInterface) Proxy.newProxyInstance(iInterface.getClass().getClassLoader(),
                iInterface.getClass().getInterfaces(),
                new InterfaceHandler(iInterface));

        String proxyResult = iInterfaceProxyObj.getResult("Hello");

        String originResult = iInterface.getResult("hello");
        Toast.makeText(this, originResult, Toast.LENGTH_SHORT).show();
    }
}
