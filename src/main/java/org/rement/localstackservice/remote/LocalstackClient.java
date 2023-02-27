package org.rement.localstackservice.remote;

import org.rement.localstackservice.model.LocalStackHealthcheckResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "localstackFeignClient", url = "${config.localstack.baseUrl}")
public interface LocalstackClient {

  @GetMapping("/_localstack/health")
  LocalStackHealthcheckResponse health();
}
