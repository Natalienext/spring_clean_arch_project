package musicsearchportal.adapter.controller.http.config;

import musicsearchportal.adapter.controller.http.interceptor.PostInterceptor;
import musicsearchportal.adapter.controller.http.interceptor.ReplyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  private final PostInterceptor postInterceptor;
  private final ReplyInterceptor replyInterceptor;

  public WebConfig(PostInterceptor postInterceptor, ReplyInterceptor replyInterceptor) {
    this.postInterceptor = postInterceptor;
    this.replyInterceptor = replyInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(postInterceptor).addPathPatterns("/create-post");
    registry.addInterceptor(replyInterceptor).addPathPatterns("/add-reply");
  }
}
