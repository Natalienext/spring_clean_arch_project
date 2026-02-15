package musicsearchportal.adapter.controller.http.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@Slf4j
public class ReplyInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(
      HttpServletRequest request, HttpServletResponse response, Object handler) {

    log.info("Запрос на добавление отклика: {} {}", request.getMethod(), request.getRequestURI());

    request.setAttribute("startTime", System.currentTimeMillis());

    return true;
  }

  @Override
  public void postHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      ModelAndView modelAndView) {

    Long startTime = (Long) request.getAttribute("startTime");
    if (startTime != null) {
      long duration = System.currentTimeMillis() - startTime;

      log.info(
          "Отклик добавлен {} {} -> {} ({} мс)",
          request.getMethod(),
          request.getRequestURI(),
          response.getStatus(),
          duration);
    }
  }
}
