// IServiceFetcher.aidl
package com.duapps.f.lib.server.interfaces;

interface IServiceFetcher {
    IBinder getService(String name);
    void addService(String name,in IBinder service);
    void removeService(String name);
}