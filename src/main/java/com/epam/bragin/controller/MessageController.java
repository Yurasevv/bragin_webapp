package com.epam.bragin.controller;

import com.epam.bragin.exception.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
public class MessageController {

    public List<Map<String, String>> messages = new ArrayList<Map<String, String>>() {{
        add(new HashMap<String, String>(){{put("id", "1"); put("text", "First"); }});
        add(new HashMap<String, String>(){{put("id", "2"); put("text", "Second"); }});
        add(new HashMap<String, String>(){{put("id", "3"); put("text", "Third"); }});

    }};

    @GetMapping
    public List<Map<String, String>> list() {
        return messages;
    }

    @GetMapping("{id}")
    public Map<String, String> getId(@PathVariable String id) {
        return getMessage(id);
    }

    private Map<String, String> getMessage(String id) {
        return messages.stream()
                .filter(message -> message.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String> message) {

        messages.add(message);

        return message;
    }

    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> message) {
        Map<String, String> messageFromDb = getMessage(message.get("id"));

        messageFromDb.putAll(message);
        messageFromDb.put("id", id);

        return messageFromDb;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        Map<String, String> message = getMessage(id);

        messages.remove(message);
    }
}
