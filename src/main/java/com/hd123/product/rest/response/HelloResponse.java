package com.hd123.product.rest.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HelloResponse implements Serializable {
  private static final long serialVersionUID = 1238080727438791681L;

  private String result;

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

}
