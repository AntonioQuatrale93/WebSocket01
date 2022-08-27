package it.develhope.WebSocket01.Controller;

import it.develhope.WebSocket01.entities.MessageDto;
import it.develhope.WebSocket01.entities.SimpleMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class NotificationController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @PostMapping("/notification")
    public ResponseEntity sendNotification(@RequestBody MessageDto messageDto){
        SimpleMessageDto simpleMessageDto = new SimpleMessageDto("Type: " +messageDto.getType() + " Message: " + messageDto.getMessage());
        simpMessagingTemplate.convertAndSend("/topic/broadcast", simpleMessageDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @MessageMapping("/hello")
    @SendTo("/topic/broadcast")
    public SimpleMessageDto handleMessageFromWebSocket(MessageDto messageDto){
        System.out.println("Arrived something on /app/hello - " + messageDto);
        return new SimpleMessageDto("From " + messageDto.getType() + " - Text " + messageDto.getMessage());
    }



}
