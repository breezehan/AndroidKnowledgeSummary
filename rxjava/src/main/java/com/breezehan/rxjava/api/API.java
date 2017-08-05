package com.breezehan.rxjava.api;

import com.breezehan.rxjava.entity.LoginRequest;
import com.breezehan.rxjava.entity.LoginResponse;
import com.breezehan.rxjava.entity.RegisterRequest;
import com.breezehan.rxjava.entity.RegisterResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;

/**
 * Author  hands
 * Description
 * Date    2017/7/29 11:36
 * Version
 */

public interface API {
    @GET("./")
    Observable<LoginResponse> login(@Body LoginRequest request);

    @GET
    Observable<RegisterResponse> register(@Body RegisterRequest request);
}
