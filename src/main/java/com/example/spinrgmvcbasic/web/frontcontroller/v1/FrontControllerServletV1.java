package com.example.spinrgmvcbasic.web.frontcontroller.v1;

import com.example.spinrgmvcbasic.web.frontcontroller.v1.controller.MemberFormControllerV1;
import com.example.spinrgmvcbasic.web.frontcontroller.v1.controller.MemberListControllerV1;
import com.example.spinrgmvcbasic.web.frontcontroller.v1.controller.MemberSaveControllerV1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV1", urlPatterns="/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {

    //특정 url이 호출되면 v1 컨트롤러 구현하도록.
    //String이 호출되는 url이고 ControllerV1이 실행되는 부분.
    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    public FrontControllerServletV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV1.service");

        //호출된 컨트롤러를 반환.
        String requestURI = request.getRequestURI();
        ControllerV1 controller = controllerMap.get(requestURI);
        if (controller == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        //다형성으로 process 쓴 것을 여기서 활용.
        //다형성을 쓰지 않았다면, 위에서 controller 객체를 생성할 떄, 일일이 URI에 맞는 것을 객체로 생성했어야 할것.
        controller.process(request, response);
    }
}
