package my_api.configuration;
// **********************************************************************
// Code generated by AlchemyJ Compiler.
// PLEASE DO NOT EDIT THIS FILE.
// **********************************************************************

import com.axisoft.alchemyj.execution.api.executor.WorkbookExecutor;
import com.axisoft.alchemyj.execution.common.exception.AlchemyjFormulaExecutionException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Aspect
@Component
@Slf4j
public class ServiceAopHandler {

  @Autowired private ObjectMapper objectMapper;

  @Autowired private AlchemyjProperties alchemyjProperties;

  /** Method for spring point cut */
  @Pointcut("execution(public * my_api.service..*.*(..))")
  public void pointCut() {
    /** No method Content -> only for spring AOP point cut * */
  }

  @Around("pointCut()")
  public Object doAroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
    // Before
    String className = joinPoint.getSignature().getDeclaringTypeName();
    String methodName = joinPoint.getSignature().getName();
    Object[] args = joinPoint.getArgs();
    String functionPointName = (String) args[0];
    WorkbookExecutor wbExecutor = (WorkbookExecutor) args[args.length - 1];
    if (log.isDebugEnabled()) {
      log.debug(
          "[class: {}, Method: {}, function point name: {}] service started, input args details: {}",
          className,
          methodName,
          functionPointName,
          Arrays.toString(joinPoint.getArgs()));
    }
    try {
      Object result = joinPoint.proceed(joinPoint.getArgs());
      // After
      if (log.isDebugEnabled()) {
        log.debug(
            "[class: {}, Method: {}, function point name: {}] service end, return details: {}",
            className,
            methodName,
            functionPointName,
            objectMapper.writeValueAsString(result));
      }
      return result;
    } catch (AlchemyjFormulaExecutionException ex) {
      log.error(
          "AlchemyJ Formula Execution Exception throws on [class: {}, Method: {}, function point name: {}], [formula errors: {}, user raise error: {}]",
          className,
          methodName,
          functionPointName,
          ex.getFormulaErrors(),
          ex.getUserRaiseErrors());
      throw ex;
    } catch (Throwable ex) {
      String msg =
          String.format(
              "Unexpect Exception throws on [class: %s, Method: %s, function point name: %s]",
              className, methodName, functionPointName);
      log.error(msg, ex);
      throw ex;
    } finally {
      this.generateDebugExcelIfAllow(functionPointName, wbExecutor);
    }
  }

  private void generateDebugExcelIfAllow(String functionPointName, WorkbookExecutor wbExecutor) {
    if (alchemyjProperties.isGenerateDebugExcel()) {
      String excelLogPath = alchemyjProperties.getDebugExcelLogPath();
      String absoluteFolderPath = calculatePath(excelLogPath);
      log.info(
          "Configure dump Excel folder: {}, absolute path: {}", excelLogPath, absoluteFolderPath);
      if (!StringUtils.endsWithIgnoreCase(absoluteFolderPath, "\\")
          && !StringUtils.endsWithIgnoreCase(absoluteFolderPath, "/")) {
        absoluteFolderPath = absoluteFolderPath + File.separator;
      }
      String absoluteExcelFilePath =
          absoluteFolderPath
              + functionPointName
              + "_"
              + DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS")
              + ".xlsx";
      wbExecutor.save2File(absoluteExcelFilePath);
    }
  }

  private String calculatePath(String sourcePath) {
    try {
      return Paths.get(sourcePath).toFile().getCanonicalPath();
    } catch (Exception e) {
      return Paths.get(sourcePath).toFile().getAbsolutePath();
    }
  }
}