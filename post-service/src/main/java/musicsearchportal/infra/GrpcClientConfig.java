package musicsearchportal.infra;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import userservice.proto.UserServiceGrpc;

@Configuration
public class GrpcClientConfig {

  @Value("${user.service.host:localhost}")
  private String userServiceHost;

  @Value("${user.service.port:9090}")
  private int userServicePort;

  @Bean(destroyMethod = "shutdown")
  public ManagedChannel userServiceChannel() {
    return ManagedChannelBuilder.forAddress(userServiceHost, userServicePort)
        .usePlaintext()
        .build();
  }

  @Bean
  public UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub(
      ManagedChannel userServiceChannel) {
    return UserServiceGrpc.newBlockingStub(userServiceChannel);
  }
}
