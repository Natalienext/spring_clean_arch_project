package musicsearchportal.infra;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@Slf4j
public class HttpLoggingInterceptor implements HandlerInterceptor {

  private static final String START_TIME_ATTRIBUTE = "startTime";

  @Override
  public boolean preHandle(
      HttpServletRequest request, HttpServletResponse response, Object handler) {

    String method = request.getMethod();
    String uri = request.getRequestURI();

    log.info("HTTP запрос: {} {}", method, uri);

    request.setAttribute(START_TIME_ATTRIBUTE, System.currentTimeMillis());

    return true;
  }

  @Override
  public void postHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      ModelAndView modelAndView) {

    Long startTime = (Long) request.getAttribute(START_TIME_ATTRIBUTE);
    if (startTime != null) {
      long duration = System.currentTimeMillis() - startTime;
      String method = request.getMethod();
      String uri = request.getRequestURI();

      log.info("HTTP ответ: {} {} -> {} ({} мс)", method, uri, response.getStatus(), duration);
    }
  }
}
