package com.xti.spring.cloud.heroku.discovery.example;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/note")
public class NoteController {

    private final CommandGateway commandGateway;

    @Autowired
    public NoteController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @RequestMapping(method = RequestMethod.POST)
    public CreateNoteResponse createAbnormalityCommand(@RequestBody CreateNoteRequest createNoteRequest){
        commandGateway.sendAndWait(new CreateNoteCommand(createNoteRequest.getId(), createNoteRequest.getText()));
        return new CreateNoteResponse(createNoteRequest.getId());
    }
}
