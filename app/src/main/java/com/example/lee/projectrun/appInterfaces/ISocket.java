package com.example.lee.projectrun.appInterfaces;

/**
 * Created by urben on 2/19/16.
 */
public interface ISocket {
    public String sendHttpRequest(String params);
    public int startListening(int port);
    public void stopListening();
    public void exit();
    public int getListeningPort();

}
