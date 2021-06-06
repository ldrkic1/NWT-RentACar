package ba.unsa.etf.vehiclemicroservice.demo.Interceptors;

import ba.unsa.etf.grpc.ActionType;
import ba.unsa.etf.grpc.SystemEventResponse;
import ba.unsa.etf.grpc.SystemEventsRequest;
import ba.unsa.etf.grpc.actionGrpc;
import ba.unsa.etf.vehiclemicroservice.demo.Exception.HttpServletResponseCopier;
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
import java.util.Calendar;
import java.util.Enumeration;
@ComponentScan
public class VehicleServiceInterceptor implements HandlerInterceptor {
    public String poruka = "Nice";

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Qualifier("eurekaClient")
    @Autowired
    private EurekaClient eurekaClient;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception exception) throws Exception {

        String action = request.getMethod();
        String[] requestArray = request.getRequestURI().split("/");
        String resource = "";

        for (int i = 1; i < requestArray.length; i++) {
            if (requestArray[i].equals(requestArray[requestArray.length - 1]))
                resource += requestArray[i];
            else
                resource += requestArray[i] + "/";
        }

        String parameters = getParameters(request);
        resource += parameters;

        /*if(resource.charAt(resource.length()-1)=='?')
            resource=resource.substring(0, resource.length()-1);*/

        //String resource = request.getRequestURI().split("/")[1]+"/"+request.getRequestURI().split("/")[2];
       if (response.getCharacterEncoding() == null) {
            response.setCharacterEncoding("UTF-8");
        }
        HttpServletResponseCopier responseCopier = new HttpServletResponseCopier(response);

        try {
            responseCopier.flushBuffer();
        } finally

        {
        byte[] copy = responseCopier.getCopy();
        System.out.println(new String(copy, response.getCharacterEncoding()));
        String actionResponse = String.valueOf(response.getStatus());
        System.out.println("**** afterCompletion ****");
        System.out.println("**** parameters: " + parameters);
        System.out.println(action + " " + resource + " " + actionResponse);
        addEvent(1L, action(action), resource, actionResponse, actionResponse);
    }
    }



    private String getParameters(HttpServletRequest request) {
        StringBuffer posted = new StringBuffer();
        Enumeration<?> e = request.getParameterNames();
        /*if (e != null) {
            posted.append("?");
        }*/
        if(e.hasMoreElements()){
            posted.append("?");
        }
        while (e.hasMoreElements()) {
            if (posted.length() > 1) {
                posted.append("&");
            }
            String curr = (String) e.nextElement();
            posted.append(curr + "=");
            if (curr.contains("password")) {
                posted.append("**********");
            } else {
                posted.append(request.getParameter(curr));
            }
        }

        return posted.toString();
    }


    public ActionType action(String actionType){
        switch (actionType){
            case "GET":
                return ActionType.GET;
            case "POST":
                return ActionType.CREATE;
            case "PUT":
                return ActionType.UPDATE;
            case "DELETE":
                return ActionType.DELETE;
            default:
                return null;
        }
    }

    public void addEvent(Long idKorisnik, ActionType tipAkcije, String nazivResursa, String actionResponse, String poruka1) {

        try {
            InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("system-events", false);
            System.out.println(instanceInfo.getIPAddr() + " : " + instanceInfo.getPort() );
            ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8091).usePlaintext().build();
            //ManagedChannel channel = ManagedChannelBuilder.forAddress(instanceInfo.getIPAddr(), 8091).usePlaintext().build();
            actionGrpc.actionBlockingStub stub=actionGrpc.newBlockingStub(channel);
            Calendar c=Calendar.getInstance();
            String ts=c.getTime().toString();
            SystemEventsRequest request = SystemEventsRequest.newBuilder()
                    .setTimeStamp(ts)
                    .setMicroservice("vehicle-service")
                    .setIdKorisnik(idKorisnik)
                    .setAction(tipAkcije.toString())
                    .setResource(nazivResursa)
                    .setResponse(actionResponse)
                    .setMessage(poruka)
                    .build();
            System.out.println(request.getAction() );
            System.out.println("ID: " + idKorisnik);
            System.out.println("Vrijeme: " + ts);
            System.out.println(nazivResursa);
            System.out.println("MESSAGE " + poruka);
            SystemEventResponse response=stub.logAction(request);
            System.out.println(response.getResponseTypeValue());
            System.out.println(response.getResponseContent());
            this.setPoruka("");
            channel.shutdown();
        }
        catch(Exception e) {
            System.out.println("Greska u grpc komunikaciji! " + e.getMessage() + " " + e.getCause() + " " + e.getStackTrace());
        }}}