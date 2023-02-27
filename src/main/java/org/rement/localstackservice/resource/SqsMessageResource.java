package org.rement.localstackservice.resource;

import com.amazonaws.services.sqs.model.DeleteMessageResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.SendMessageResult;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.rement.localstackservice.model.SqsModel;
import org.rement.localstackservice.service.SqsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/sqs/message")
public class SqsMessageResource {

  private SqsService sqsService;

  @PostMapping
  public SendMessageResult send(@RequestBody SqsModel sqsModel)
      throws ExecutionException, InterruptedException, TimeoutException {
    return sqsService.sendMessage(sqsModel.getQueueName(), sqsModel.getMessage());
  }

  @GetMapping
  public List<Message> receive(@RequestParam("queryUrl") String queueUrl)
      throws ExecutionException, InterruptedException,
      TimeoutException {
    String decodedUrl = URLDecoder.decode(queueUrl, StandardCharsets.UTF_8);
    return sqsService.receiveMessage(decodedUrl);
  }

  @DeleteMapping
  public DeleteMessageResult delete(@RequestParam("queueUrl") String queueUrl,
                                    @RequestParam("receiptHandle") String receiptHandle)
      throws ExecutionException, InterruptedException, TimeoutException {
    String decodedUrl = URLDecoder.decode(queueUrl, StandardCharsets.UTF_8);
    return sqsService.deleteMessage(decodedUrl, receiptHandle);
  }

}
