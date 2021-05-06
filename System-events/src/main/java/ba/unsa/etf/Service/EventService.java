package ba.unsa.etf.Service;

import ba.unsa.etf.Repository.GrpcRepository;
import ba.unsa.etf.Models.Event;
import ba.unsa.etf.grpc.actionGrpc;
import ba.unsa.etf.grpc.SystemEventResponse;
import ba.unsa.etf.grpc.SystemEventsRequest;
import ba.unsa.etf.grpc.*;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@GRpcService
public class EventService extends actionGrpc.actionImplBase {
    @Autowired
    GrpcRepository grpcRepository;

    @Override
    public void logAction(SystemEventsRequest request, StreamObserver<SystemEventResponse> responseObserver) {
        System.out.println("*** logAction ***");
        SystemEventResponse.Builder response =  SystemEventResponse.newBuilder();
        try {
            Event event = new Event(request.getTimeStamp(), request.getMicroservice(), request.getAction(),request.getResource(), request.getResponse(), request.getIdKorisnik());
            //spasimo event u bazu
            System.out.println("");
            System.out.println(event.getResource() + " " + event.getMicroservice() + " " + event.getResponse());
            grpcRepository.save(event);
            response.setResponseContent("Event is saved").setResponseType(ResponseType.SUCCESS);
        }
        catch (Exception e) {
            //doslo je do greske
            response.setResponseContent("It's an error!").setResponseType(ResponseType.ERROR);
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        //zatvorimo konekciju
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();

    }

}
