package com.example.rabbitmq.rabbit_app;

import java.util.concurrent.TimeUnit;

import com.example.rabbitmq.RabbitMqApplication;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

  private final RabbitTemplate rabbitTemplate;
  private final Receiver receiver;

  public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
    this.receiver = receiver;
    this.rabbitTemplate = rabbitTemplate;
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("Sending message...");
    rabbitTemplate.convertAndSend(RabbitMqApplication.topicExchangeName, "foo.bar.baz", "Hello from RabbitMQ!");
    receiver.getLatch().await(1000, TimeUnit.MILLISECONDS);

    rabbitTemplate.convertAndSend(RabbitMqApplication.topicExchangeName, "foo.bar.baz", "Hello from RabbitMQ!");
    receiver.getLatch().await(1000, TimeUnit.MILLISECONDS);
  }

}