package org.rement.localstackservice.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClient;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class AwsConfig {

  @Value("${config.localstack.region}")
  private final String region;

  @Bean
  public AWSCredentialsProvider getAwsCredentialsProvider(
      @Value("${config.localstack.accessKey}") String accessKey,
      @Value("${config.localstack.secretKey}") String secretKey) {
    return new AWSCredentialsProviderChain(
        new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)),
        new ProfileCredentialsProvider()
    );
  }

  @Bean
  public AmazonSQSAsync indexSqsClient(AWSCredentialsProvider credentialsProvider,
                                       @Value("${config.localstack.sqs.scheme}") String protocol,
                                       @Value("${config.localstack.sqs.url}") String sqsEndpoint) {
    ClientConfiguration clientConfiguration = new ClientConfiguration();
    clientConfiguration.setProtocol(
        Protocol.HTTPS.toString().equalsIgnoreCase(protocol) ? Protocol.HTTPS : Protocol.HTTP);
    return AmazonSQSAsyncClient.asyncBuilder()
                               .withCredentials(credentialsProvider)
                               .withClientConfiguration(clientConfiguration)
                               .withEndpointConfiguration(
                                   new AwsClientBuilder.EndpointConfiguration(sqsEndpoint, region))
                               .build();
  }
}
