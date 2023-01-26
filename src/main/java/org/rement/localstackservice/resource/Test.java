package org.rement.localstackservice.resource;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import lombok.AllArgsConstructor;

import org.rement.localstackservice.service.SqsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class Test {

  private SqsService sqsService;

  @PostMapping(value = "/send")
  public Object send(@RequestBody TestEnt testEnt) throws ExecutionException, InterruptedException, TimeoutException {
    return sqsService.sendMessage(testEnt.getQueueName(), testEnt.getMessage());
  }

  @PostMapping(value = "/receive")
  public Object receive(@RequestBody TestEnt testEnt) throws ExecutionException, InterruptedException,
      TimeoutException {
    return sqsService.receiveMessage(testEnt.getQueueName());
  }

  @GetMapping("/queues")
  public List<String> queues() {
    return sqsService.getAllQueueUrls();
  }

}
