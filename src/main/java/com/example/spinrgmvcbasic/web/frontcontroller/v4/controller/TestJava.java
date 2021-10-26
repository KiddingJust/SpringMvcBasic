package com.example.spinrgmvcbasic.web.frontcontroller.v4.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestJava {

    public static void main(String[] args) {

        Map<String, String> model = new HashMap<>(); //추가
        testParamChange(model);
        System.out.println(model.get("gaiga")); //korea

        int n = 4;
        String str = "gaiga";
        List<Integer> intArr = new ArrayList<>();
        add(n, str, intArr);
        System.out.println("n: " + n);  //4
        System.out.println("str: " + str);  //giaga
        System.out.println("intArr get(0): " + intArr.get(0));  //3
        //다른 이유는? 객체를 넘기느냐 안넘기느냐의 차이
        //객체의 참조값이 넘어가므로 process 메서드로 전달된model과 process 메서드 내 model은 같은 객체를 가리킨다.
    }

    static void testParamChange(Map<String, String> model){
        model.put("gaiga", "korea");
    }

    static void add(int n, String str, List<Integer> intArr){
        n = n+4;
        str = str+"test";
        intArr.add(3);
    }
}
