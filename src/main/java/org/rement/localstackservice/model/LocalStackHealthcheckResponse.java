package org.rement.localstackservice.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.Map;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalStackHealthcheckResponse {

  private Map<String, String> services;
  private Instant effectiveDateTime = Instant.now();
}
