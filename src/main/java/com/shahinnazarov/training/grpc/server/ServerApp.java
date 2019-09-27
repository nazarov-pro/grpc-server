package com.shahinnazarov.training.grpc.server;

import com.shahinnazarov.training.grpc.server.services.TestGrpcService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.File;

public class ServerApp {
    public static void main(String[] args) {
        try {
            File chain = new File("/home/shahin/IdeaProjects/grpc-client/src/main/resources/public.pem");
            File prv = new File("/home/shahin/IdeaProjects/grpc-client/src/main/resources/private.pem");
            Server server = ServerBuilder.forPort(8443)
                    .useTransportSecurity(chain, prv)
                    .addService(new TestGrpcService())
                    .intercept(new AuthorizationProvider())
                    .build();
            server.start();
            System.out.println("Server started");

            server.awaitTermination();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
