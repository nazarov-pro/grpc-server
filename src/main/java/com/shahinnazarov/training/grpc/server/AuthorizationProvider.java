package com.shahinnazarov.training.grpc.server;

import io.grpc.*;

public class AuthorizationProvider implements ServerInterceptor {

    public static final Context.Key<Object> USER_ROLE = Context.key("user_role");

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call,
                                                                 Metadata headers,
                                                                 ServerCallHandler<ReqT, RespT> next) {
        final String auth_token = headers.get(Metadata.Key.of("auth_token", Metadata.ASCII_STRING_MARSHALLER));
        Context context = null;
        if (auth_token == null) {
            throw new StatusRuntimeException(Status.FAILED_PRECONDITION);
        } else if (auth_token.equals("user_token")) {
            context = Context.current().withValue(USER_ROLE, "user");
        } else if (auth_token.equals("admin_token")) {
            context = Context.current().withValue(USER_ROLE, "admin");
        } else {
            throw new StatusRuntimeException(Status.FAILED_PRECONDITION);
        }
        return Contexts.interceptCall(context, call, headers, next);
    }
}
