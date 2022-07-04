package com.example.producer;

import com.example.dto.Order;
import com.example.dto.OrderStatus;
import com.example.messageConfig.MessageConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderPublisher {

    @Autowired
    RabbitTemplate template;



    @PostMapping(value = "/{restName}")
    public String bookOrder(@RequestBody Order order, @PathVariable String restName){
        order.setOrderId(UUID.randomUUID().toString());
        System.out.println("hello after hit");


        OrderStatus orderStatus = new OrderStatus(
                order, "progress", "order at"+restName);
        template.convertAndSend(MessageConfig.EXCHANGE, MessageConfig.ROUTING_QUEUE, orderStatus);
        return "Successfully !!";
    }
}
