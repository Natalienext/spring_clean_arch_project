package userservice.infra;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import userservice.adapter.controller.grpc.UserGrpcController;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class GrpcServerConfig {

  private final UserGrpcController userGrpcController;

  @Value("${grpc.server.port:9090}")
  private int grpcPort;

  private Server grpcServer;

  @PostConstruct
  public void startGrpcServer() throws IOException {
    log.info("Запуск gRPC сервера на порту {}...", grpcPort);

    grpcServer =
        ServerBuilder.forPort(grpcPort)
            .addService(userGrpcController) // регистрируем наш сервис
            .build()
            .start();

    log.info("gRPC сервер успешно запущен на порту {}", grpcPort);

    Runtime.getRuntime()
        .addShutdownHook(
            new Thread(
                () -> {
                  log.info("Получен сигнал завершения, останавливаем gRPC сервер...");
                  GrpcServerConfig.this.stopGrpcServer();
                }));
  }

  @PreDestroy
  public void stopGrpcServer() {
    if (grpcServer != null) {
      log.info("Останавливаем gRPC сервер...");
      grpcServer.shutdown();
      log.info("gRPC сервер остановлен");
    }
  }
}
