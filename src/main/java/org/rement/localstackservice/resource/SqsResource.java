package org.rement.localstackservice.resource;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.DeleteQueueResult;
import com.amazonaws.services.sqs.model.GetQueueAttributesResult;

import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.rement.localstackservice.model.SqsModel;
import org.rement.localstackservice.service.SqsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/sqs")
public class SqsResource {

  private SqsService sqsService;

  @PostMapping(value = "/send")
  public Object send(@RequestBody SqsModel sqsModel) throws ExecutionException, InterruptedException, TimeoutException {
    return sqsService.sendMessage(sqsModel.getQueueName(), sqsModel.getMessage());
  }

  @PostMapping(value = "/receive")
  public Object receive(@RequestBody String queueUrl) throws ExecutionException, InterruptedException,
      TimeoutException {
    return sqsService.receiveMessage(queueUrl);
  }

  @GetMapping("/queues")
  public List<String> queues() {
    return sqsService.getAllQueueUrls();
  }

  @GetMapping("/queue/{queueUrl}")
  public GetQueueAttributesResult getQueueDetails(@PathVariable String queueUrl)
      throws ExecutionException, InterruptedException, TimeoutException {
    String decodedUrl = URLDecoder.decode(queueUrl, StandardCharsets.UTF_8);
    return sqsService.getQueueDetails(decodedUrl);
  }

  @PostMapping("/queue")
  public CreateQueueResult createQueue(@RequestBody String queueName)
      throws ExecutionException, InterruptedException, TimeoutException {
    return sqsService.createQueue(queueName);
  }

  @DeleteMapping("/queue/{queueName}")
  public DeleteQueueResult deleteQueue(@PathVariable String queueName)
      throws ExecutionException, InterruptedException, TimeoutException {
    return sqsService.deleteQueue(queueName);
  }

}
