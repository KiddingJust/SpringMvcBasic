package com.example.spinrgmvcbasic.web.frontcontroller.v4.controller;

import com.example.spinrgmvcbasic.domain.Member;
import com.example.spinrgmvcbasic.domain.MemberRepository;
import com.example.spinrgmvcbasic.web.frontcontroller.ModelView;
import com.example.spinrgmvcbasic.web.frontcontroller.v4.ControllerV4;

import java.util.Map;

public class MemberSaveControllerV4 implements ControllerV4 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        model.put("member", member);
        //아래와 같이 ModelView 불러와서 model에 put 해줄 필요 없음. 그냥 view name만 반환
//        ModelView mv = new ModelView("save-result");
//        mv.getModel().put("member", member);
        return "save-result";
    }
}
