package com.hd123.product.rest.common;

import java.text.SimpleDateFormat;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * 定制ObjectMapper
 * 
 * @author liyue
 */
@Provider
@Consumes({
    ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8 })
@Produces({
    ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8 })
public class ObjectMapperCustomizer implements ContextResolver<ObjectMapper> {

  private static final String I18N_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
  private static ObjectMapper objectMapper = null;

  public ObjectMapperCustomizer() {
    super();
  }

  @Override
  public synchronized ObjectMapper getContext(Class<?> type) {
    if (objectMapper == null) {
      objectMapper = new ObjectMapper();
      SimpleDateFormat i18n_date_format = new SimpleDateFormat(I18N_DATE_FORMAT);
      objectMapper.setDateFormat(i18n_date_format);
      // objectMapper.enableDefaultTyping();
      objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
      objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
      objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
      objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
      objectMapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false);
      objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
      objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
      objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      objectMapper.configure(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY, true);
      objectMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
      objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
      // objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES,
      // true);
    }

    return objectMapper;
  }

}
