package my_api.configuration;
// **********************************************************************
// Code generated by AlchemyJ Compiler.
// PLEASE DO NOT EDIT THIS FILE.
// **********************************************************************

import com.google.common.base.Predicates;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxSwaggerConfiguration implements WebMvcConfigurer {

  private final Map<Integer, String> httpResponseCodeMap =
      new HashMap<Integer, String>() {
        {
          put(200, "OK");
          put(400, "Bad Request");
          put(404, "Not Found");
          put(500, "Internal Server Error");
        }
      };

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry
        .addResourceHandler("/openapi/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/3.25.0/");
  }

  @Bean
  public Docket swaggerDocket() {
    Docket docket =
        new Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)
            .apiInfo(this.createApiInfo())
            .protocols(Sets.newHashSet("http"))
            .select()
            .paths(Predicates.not(PathSelectors.regex("/error.*")))
            .paths(PathSelectors.regex("/.*"))
            .build();

    addDefaultHttpResponseCode(docket);
    return docket;
  }

  private void addDefaultHttpResponseCode(Docket docket) {
    List<ResponseMessage> responseMessages =
        httpResponseCodeMap
            .entrySet()
            .parallelStream()
            .map(e -> new ResponseMessageBuilder().code(e.getKey()).message(e.getValue()).build())
            .collect(Collectors.toList());

    docket
        .globalResponseMessage(RequestMethod.GET, responseMessages)
        .globalResponseMessage(RequestMethod.POST, responseMessages)
        .globalResponseMessage(RequestMethod.PUT, responseMessages)
        .globalResponseMessage(RequestMethod.DELETE, responseMessages)
        .globalResponseMessage(RequestMethod.PATCH, responseMessages);
  }

  private ApiInfo createApiInfo() {
    Contact contact = new Contact("", "", "");
    return new ApiInfoBuilder()
        .contact(contact)
        .title("My API Document Title")
        .description("My API Document Description")
        .version("1.0.0")
        .license("")
        .licenseUrl("")
        .build();
  }

  private List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Collections.singletonList(new SecurityReference("Authorization", authorizationScopes));
  }
}