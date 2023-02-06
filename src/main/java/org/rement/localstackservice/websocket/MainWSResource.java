package org.rement.localstackservice.websocket;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.rement.localstackservice.model.LocalStackHealthcheckResponse;
import org.rement.localstackservice.remote.LocalstackClient;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@AllArgsConstructor
public class MainWSResource {

  private LocalstackClient localstackClient;
  private final SimpMessagingTemplate template;

  @Scheduled(fixedRate = 30000)
  public void sendMessage() {
    try {
      log.info("Start sending healthcheck to ws");
      LocalStackHealthcheckResponse health = localstackClient.health();
      this.template.convertAndSend("/dashboard", health);
    } catch (Exception e) {
      log.error("Something went wrong: ", e);
    }
  }

}
