package com.china.center.oa.openservice.service;

import com.china.center.oa.openservice.service.result.WsResult;


public interface LoginService 
{
    WsResult login(String name, String password, String rand, String key, String randKey);
}
