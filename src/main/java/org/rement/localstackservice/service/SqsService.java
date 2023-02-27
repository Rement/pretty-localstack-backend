package org.rement.localstackservice.service;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.DeleteMessageResult;
import com.amazonaws.services.sqs.model.DeleteQueueResult;
import com.amazonaws.services.sqs.model.GetQueueAttributesRequest;
import com.amazonaws.services.sqs.model.GetQueueAttributesResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

import java.util.ArrayList;
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
    List<Message> response = new ArrayList<>();
    int retrievedMessages;
    do {
      List<Message> messages = getMessages(receiveMessageRequest);
      retrievedMessages = messages.size();
      response.addAll(messages);
    } while (retrievedMessages != 0);
    return response;
  }

  private List<Message> getMessages(ReceiveMessageRequest receiveMessageRequest)
      throws InterruptedException, ExecutionException, TimeoutException {
    return amazonSQSAsyncClient.receiveMessageAsync(receiveMessageRequest)
                               .get(5, TimeUnit.SECONDS)
                               .getMessages();
  }

  public GetQueueAttributesResult getQueueDetails(String queueUrl) throws ExecutionException, InterruptedException, TimeoutException {
    GetQueueAttributesRequest request = new GetQueueAttributesRequest();
    request.setQueueUrl(queueUrl);
    List<String> allAttributes = List.of("All");
    request.setAttributeNames(allAttributes);
    return amazonSQSAsyncClient.getQueueAttributesAsync(request).get(5, TimeUnit.SECONDS);
  }

  public CreateQueueResult createQueue(String queueName) throws ExecutionException, InterruptedException, TimeoutException {
    return amazonSQSAsyncClient.createQueueAsync(queueName).get(5, TimeUnit.SECONDS);
  }

  public DeleteQueueResult deleteQueue(String queueName) throws ExecutionException, InterruptedException, TimeoutException {
    return amazonSQSAsyncClient.deleteQueueAsync(queueName).get(5, TimeUnit.SECONDS);
  }

  public DeleteMessageResult deleteMessage(String queueUrl, String receiptHandle)
      throws ExecutionException, InterruptedException, TimeoutException {
    return amazonSQSAsyncClient.deleteMessageAsync(queueUrl, receiptHandle).get(5, TimeUnit.SECONDS);
  }

}
