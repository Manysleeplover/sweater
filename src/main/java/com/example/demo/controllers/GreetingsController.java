package com.example.demo.controllers;

import com.example.demo.domain.Message;
import com.example.demo.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;

@Controller
public class GreetingsController {

    @Autowired
    private MessageRepo messageRepo;

    @GetMapping()
    public String main(Map<String, Object> model){
        Iterable<Message> messages = messageRepo.findAll();
        model.put("messages", messages);
        return "greeting";
    }

    @PostMapping
    public String add(@RequestParam String text, @RequestParam String tag, Map<String, Object> model){
        Message message = new Message(text, tag);
        messageRepo.save(message);
        Iterable<Message> messages = messageRepo.findAll();
        model.put("messages", messages);
        return "greeting";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String tag, Map<String, Object> model){
        Iterable<Message> messages =  messageRepo.findByTag(tag);
        if(tag != null && tag.isEmpty()){
            model.put("messages", messages);
        } else {
            messages = messageRepo.findAll();
        }
        return "greeting";
    }
}
