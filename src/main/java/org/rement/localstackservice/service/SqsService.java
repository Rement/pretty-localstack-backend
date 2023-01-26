package org.rement.localstackservice.service;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SqsService {

  private final AmazonSQSAsync amazonSQSAsyncClient;

  public List<String> getAllQueueUrls() {
    return amazonSQSAsyncClient.listQueues().getQueueUrls();
  }

  public SendMessageResult sendMessage(String queueUrl, String messageBody)
      throws ExecutionException, InterruptedException, TimeoutException {
    return amazonSQSAsyncClient.sendMessageAsync(queueUrl, messageBody).get(5, TimeUnit.SECONDS);
  }

  public List<Message> receiveMessage(String queueUrl)
      throws ExecutionException, InterruptedException, TimeoutException {
    ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl)
        .withMaxNumberOfMessages(10);
    return amazonSQSAsyncClient.receiveMessageAsync(receiveMessageRequest).get(5, TimeUnit.SECONDS).getMessages();
  }

}
