package com.hd123.product.rest.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Priority;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Rest日志跟踪
 * 
 * @author liyue
 */
@Provider
@Priority(Integer.MIN_VALUE)
public class LoggingFilter implements ContainerRequestFilter, ClientRequestFilter,
    ContainerResponseFilter, ClientResponseFilter, WriterInterceptor, ReaderInterceptor {
  private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

  @Override
  public void filter(ClientRequestContext context) throws IOException {
    logHttpHeaders(context.getUri().toString(), context.getStringHeaders());
  }

  @Override
  public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext)
      throws IOException {
    logHttpHeaders(requestContext.getUri().toString(), responseContext.getHeaders());
  }

  @Override
  public void filter(ContainerRequestContext context) throws IOException {
    logHttpHeaders(context.getUriInfo().getPath().toString(), context.getHeaders());
  }

  @Override
  public void filter(ContainerRequestContext requestContext,
      ContainerResponseContext responseContext) throws IOException {
    logHttpHeaders(requestContext.getUriInfo().getPath().toString(),
        responseContext.getStringHeaders());
  }

  @Override
  public Object aroundReadFrom(ReaderInterceptorContext context) throws IOException,
      WebApplicationException {
    byte[] buffer = IOUtils.toByteArray(context.getInputStream());
    logger.info("The contents of request body is: \n" + new String(buffer, "UTF-8") + "\n");
    context.setInputStream(new ByteArrayInputStream(buffer));
    return context.proceed();
  }

  @Override
  public void aroundWriteTo(WriterInterceptorContext context) throws IOException,
      WebApplicationException {
    OutputStreamWrapper wrapper = new OutputStreamWrapper(context.getOutputStream());
    context.setOutputStream(wrapper);
    context.proceed();
    logger.info("The contents of response body is: \n" + new String(wrapper.getBytes(), "UTF-8")
        + "\n");
  }

  protected void logHttpHeaders(String url, MultivaluedMap<String, String> headers) {
    StringBuilder msg = new StringBuilder("The HTTP headers are: \n");
    for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
      msg.append(entry.getKey()).append(": ");
      for (int i = 0; i < entry.getValue().size(); i++) {
        msg.append(entry.getValue().get(i));
        if (i < entry.getValue().size() - 1) {
          msg.append(", ");
        }
      }
      msg.append("\n");
    }
    msg.append("request context url=").append(url);
    logger.info(msg.toString());
  }

  protected static class OutputStreamWrapper extends OutputStream {

    private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private final OutputStream output;

    private OutputStreamWrapper(OutputStream output) {
      this.output = output;
    }

    @Override
    public void write(int i) throws IOException {
      buffer.write(i);
      output.write(i);
    }

    @Override
    public void write(byte[] b) throws IOException {
      buffer.write(b);
      output.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
      buffer.write(b, off, len);
      output.write(b, off, len);
    }

    @Override
    public void flush() throws IOException {
      output.flush();
    }

    @Override
    public void close() throws IOException {
      output.close();
    }

    public byte[] getBytes() {
      return buffer.toByteArray();
    }
  }

}