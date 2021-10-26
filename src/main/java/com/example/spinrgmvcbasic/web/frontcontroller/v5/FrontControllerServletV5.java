package com.example.spinrgmvcbasic.web.frontcontroller.v5;

import com.example.spinrgmvcbasic.web.frontcontroller.ModelView;
import com.example.spinrgmvcbasic.web.frontcontroller.MyView;
import com.example.spinrgmvcbasic.web.frontcontroller.v3.controller.MemberFormControllerV3;
import com.example.spinrgmvcbasic.web.frontcontroller.v3.controller.MemberListControllerV3;
import com.example.spinrgmvcbasic.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import com.example.spinrgmvcbasic.web.frontcontroller.v4.controller.MemberFormControllerV4;
import com.example.spinrgmvcbasic.web.frontcontroller.v4.controller.MemberListControllerV4;
import com.example.spinrgmvcbasic.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import com.example.spinrgmvcbasic.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import com.example.spinrgmvcbasic.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    //기존에는 controllerMap(이제 handlerMappingMap)에 ControllerV4가 구체적으로 들어갔는데, 이젠 Object로 넣어줌.
    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    //생성자를 통해 호출. handlerMappingMap에 추가
    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        //V4 추가
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }

    //핸들러 어댑터도 추가
    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //handlerMappingMap에서 가져와서 컨트롤러를 찾음
        Object handler = getHandler(request);
        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        //handlerAdapter도 가져와서 넣음.
        //for문 돌면서 해당 adapter가 특정 handler를 support 하는지 체크해서 가져옴.
        MyHandlerAdapter adapter = getHandlerAdapter(handler);
        //그리고 이제 adapter의 handle을 호출하여 ModelView를 반환받을 수 있음.
        ModelView mv = adapter.handle(request, response, handler);

        String viewName = mv.getViewName();
        MyView view = viewResolver(viewName);

        view.render(mv.getModel(), request, response);

    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        //MemberFormControllerV4
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler=" + handler);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
