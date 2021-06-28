package com.optical.Service;

import com.optical.bean.LoginForm;
import com.optical.bean.OpWebResult;

/**
 * Created by mary on 2021/6/1.
 */
public interface LoginService {

    public OpWebResult login(LoginForm loginForm);

    public OpWebResult userInfo(Long vendorId);

}
