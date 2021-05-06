package ba.unsa.etf.clientcaremicroservice.config;

import ba.unsa.etf.grpc.*;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;

@ComponentScan
public class Interceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {
        String action = request.getMethod();
        String resource = "";
        String temp = request.getRequestURI().split("/")[1];
        String params = getParameters(request);
        resource = request.getRequestURI().substring(request.getRequestURI().indexOf(temp));
        if(params.length()>1) resource+=params;
        String actionResponse = String.valueOf(response.getStatus());
        ActionType actionType = ActionType.GET;
        if(action.equals("POST")) actionType=ActionType.CREATE;
        else if(action.equals("PUT")) actionType = ActionType.UPDATE;
        else if(action.equals("DELETE")) actionType = ActionType.DELETE;
        System.out.println(actionType.name());
        System.out.println(action);
        System.out.println(resource);
        System.out.println(actionResponse);
        addEvent(1L, actionType, resource, actionResponse);
    }
    private String getParameters(HttpServletRequest request) {
        StringBuffer posted = new StringBuffer();
        Enumeration<?> e = request.getParameterNames();
        if (e != null) {
            posted.append("?");
        }
        while (e.hasMoreElements()) {
            if (posted.length() > 1) {
                posted.append("&");
            }
            String curr = (String) e.nextElement();
            posted.append(curr + "=");
            if (curr.contains("password")) {
                posted.append("***");
            } else {
                posted.append(request.getParameter(curr));
            }
        }

        return posted.toString();
    }
    @Qualifier("eurekaClient")
    @Autowired
    private EurekaClient eurekaClient;
    public void addEvent(Long idKorisnik, ActionType actionType, String resource, String actionResponse) {        try {
        InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("system-events", false);
        ManagedChannel channel = ManagedChannelBuilder.forAddress(instanceInfo.getIPAddr(), 8091).usePlaintext().build();
        actionGrpc.actionBlockingStub stub=actionGrpc.newBlockingStub(channel);
        Calendar c=Calendar.getInstance();
        String ts=c.getTime().toString();
        SystemEventResponse response=stub.logAction(SystemEventsRequest.newBuilder()
                .setTimeStamp(ts)
                .setMicroservice("clientcare-service")
                .setIdKorisnik(idKorisnik)
                .setAction(actionType.toString())
                .setResource(resource)
                .setResponse(actionResponse)
                .build());
        System.out.println(response.getResponseTypeValue());
        channel.shutdown();
    }
    catch(Exception e) {
        System.out.println(e.getCause());
        System.out.println(e.getMessage());
    }
    }
}
