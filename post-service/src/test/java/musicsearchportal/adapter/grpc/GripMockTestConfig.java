package musicsearchportal.adapter.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import userservice.proto.UserServiceGrpc;

@TestConfiguration
@Profile("test")
public class GripMockTestConfig {

  private static final String GRIPMOCK_HOST = "localhost";
  private static final int GRIPMOCK_PORT = 50051; // Порт из docker-compose

  @Bean
  @Primary
  public ManagedChannel testManagedChannel() {
    return ManagedChannelBuilder.forAddress(GRIPMOCK_HOST, GRIPMOCK_PORT)
        .usePlaintext() // Для тестов отключаем TLS
        .build();
  }

  @Bean
  @Primary
  public UserServiceGrpc.UserServiceBlockingStub testUserServiceStub(ManagedChannel channel) {
    return UserServiceGrpc.newBlockingStub(channel);
  }
}
