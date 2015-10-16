package com.hd123.product.rest.common;

import javax.ws.rs.core.MediaType;

/**
 * @author liyue
 */
public interface ContentType {

  public static final String APPLICATION_JSON_UTF_8 = MediaType.APPLICATION_JSON + "; "
      + MediaType.CHARSET_PARAMETER + "=UTF-8";

  public static final String TEXT_XML_UTF_8 = MediaType.TEXT_XML + "; "
      + MediaType.CHARSET_PARAMETER + "=UTF-8";

  public static final String TEXT_PLAIN_UTF_8 = MediaType.TEXT_PLAIN + "; "
      + MediaType.CHARSET_PARAMETER + "=UTF-8";

}
