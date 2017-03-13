package com.example.e610.popularmoviesstage1.Utils;

/**
 * Created by E610 on 21/09/2016.
 */
public interface NetworkResponse {


    void OnSuccess(String JsonData);
    void OnUpdate(boolean IsDataReceived);
}
