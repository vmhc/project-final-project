package com.udacity.gradle.builditbigger.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.ArrayList;

import javax.inject.Named;

/** An endpoint class we are exposing */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.gradle.udacity.com",
                ownerName = "backend.builditbigger.gradle.udacity.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String name) {
        MyBean response = new MyBean();
        response.setData("Hi, " + name);

        return response;
    }

    @ApiMethod(name = "randomJoker")
    public MyBean randomJoker(){
        initStore();

        MyBean response = new MyBean();
        response.setData(storeJoker.get((int)(Math.random()*storeJoker.size())));

        return response;
    }

    static ArrayList<String> storeJoker = new ArrayList<>();

    private static void initStore(){
        storeJoker.add("joker 0");
        storeJoker.add("joker 1");
        storeJoker.add("joker 2");
        storeJoker.add("joker 3");
        storeJoker.add("joker 4");
        storeJoker.add("joker 5");
        storeJoker.add("joker 6");
        storeJoker.add("joker 7");
        storeJoker.add("joker 8");
        storeJoker.add("joker 9");
    }

}
