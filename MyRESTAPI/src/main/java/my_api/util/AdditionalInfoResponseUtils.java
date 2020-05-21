package my_api.util;
// **********************************************************************
// Code generated by AlchemyJ Compiler.
// PLEASE DO NOT EDIT THIS FILE.
// **********************************************************************

import com.axisoft.alchemyj.execution.api.entity.RestApiAdditionInfoResponse;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import my_api.Constants;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class AdditionalInfoResponseUtils {

  public RestApiAdditionInfoResponse success(HttpServletRequest request) {
    String uri = request.getRequestURI();
    return new RestApiAdditionInfoResponse(
        Constants.AdditionalResponseStatusCode.NORMAL, new Date(), uri, null, null);
  }

  public RestApiAdditionInfoResponse userRaiseException(
      HttpServletRequest request, List<String> messages, List<String> errorCodes) {
    String uri = request.getRequestURI();
    return new RestApiAdditionInfoResponse(
        Constants.AdditionalResponseStatusCode.SERVER_USER_RAISE_EXCEPTION,
        new Date(),
        uri,
        messages,
        errorCodes);
  }

  public RestApiAdditionInfoResponse unexpectException(
      HttpServletRequest request, List<String> messages) {
    String uri = request.getRequestURI();
    return new RestApiAdditionInfoResponse(
        Constants.AdditionalResponseStatusCode.SERVER_EXCEPTION, new Date(), uri, messages, null);
  }

  public RestApiAdditionInfoResponse notFoundException(
      HttpServletRequest request, List<String> messages) {
    String uri = request.getRequestURI();
    return new RestApiAdditionInfoResponse(
        Constants.AdditionalResponseStatusCode.NOT_FOUND_EXCEPTION,
        new Date(),
        uri,
        messages,
        null);
  }
}
