package com.shahinnazarov.training.grpc.server.services;

import com.shahinnazarov.training.grpc.server.AuthorizationProvider;
import grpc.example.learn.HelloRequest;
import grpc.example.learn.HelloResponse;
import grpc.example.learn.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;

public class TestGrpcService extends HelloServiceGrpc.HelloServiceImplBase {
    @Override
    public void greet(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        System.out.println(request);
        String role = (String) AuthorizationProvider.USER_ROLE.get();
        responseObserver
                .onNext(HelloResponse
                        .newBuilder()
                        .setGreeting("Hello  " + request.getName() +", " + role)
                        .build());
        responseObserver.onCompleted();
    }
}
