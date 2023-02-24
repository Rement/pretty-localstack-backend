package org.rement.localstackservice.model;

import lombok.Data;

@Data
public class SqsModel {

  private String message;
  private String queueName;

}
