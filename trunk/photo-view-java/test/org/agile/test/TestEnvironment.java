package org.agile.test;

import java.util.Map;

import com.google.apphosting.api.ApiProxy;

public class TestEnvironment implements ApiProxy.Environment {

//    apiproxy_stub_map.apiproxy.RegisterStub('user', user_service_stub.UserServiceStub())
//    os.environ['AUTH_DOMAIN'] = 'gmail.com'
//    os.environ['USER_EMAIL'] = 'myself@appengineguy.com' # set to '' for no logged in user
//    os.environ['SERVER_NAME'] = 'testserver'
//    os.environ['SERVER_PORT'] = '80'
//    os.environ['USER_IS_ADMIN'] = '1' #admin user 0 | 1
//    os.environ['APPLICATION_ID'] = 'appId'
        
    public String getAppId() {
        return "numen";
    }

    public String getVersionId() {
        return "8.0";
    }

    public void setDefaultNamespace(String s) {
        // "http://appengine.google.com/ns/1.0";
    }

    public String getRequestNamespace() {
        return "";
    }

    public String getDefaultNamespace() {
        return "";
    }

    public String getAuthDomain() {
        return "gmail.com";
    }

    public boolean isLoggedIn() {
        return false;
    }

    public String getEmail() {
        return "agile.chen@gmail.com";
    }

    public boolean isAdmin() {
        return false;
    }

    public Map<String, Object> getAttributes() {
        return null;
    }
}
