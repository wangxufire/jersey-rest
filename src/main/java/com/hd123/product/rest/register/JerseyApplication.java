package com.hd123.product.rest.register;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

import com.hd123.product.rest.common.LoggingFilter;
import com.hd123.product.rest.common.ObjectMapperCustomizer;
import com.hd123.product.rest.controller.RestController;
import com.hd123.product.rest.exceptionmapper.CustomExceptionMapper;

/**
 * @author liyue
 */
public class JerseyApplication extends ResourceConfig {

  /**
   * Register JAX-RS application components.
   */
  public JerseyApplication() {

    // Controllers
    register(RestController.class);

    // Components
    register(LoggingFilter.class);
    register(CustomExceptionMapper.class);
    register(ObjectMapperCustomizer.class);
    register(RequestContextFilter.class);

    // validation
    property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
    property(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true);

    // resources packages
    packages(RestController.class.getPackage().getName());
  }

}
