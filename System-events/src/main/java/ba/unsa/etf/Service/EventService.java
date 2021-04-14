package ba.unsa.etf.Service;

import ba.unsa.etf.Repository.GrpcRepository;
import ba.unsa.etf.demo.Models.Event;
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

        SystemEventResponse.Builder response =  SystemEventResponse.newBuilder();
        try {
            Event event = new Event(request.getTimeStamp(), request.getMicroservice(), Integer.parseInt(request.getAction()),request.getResource(), request.getResponse(), request.getIdKorisnik());
            //spasimo event u bazu
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
