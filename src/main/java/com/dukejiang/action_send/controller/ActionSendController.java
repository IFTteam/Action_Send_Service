package com.dukejiang.action_send.controller;

import com.dukejiang.action_send.model.Transmission;
import com.dukejiang.action_send.model.request.TransmissionRequest;
import com.dukejiang.action_send.model.response.Response;
import com.dukejiang.action_send.model.response.SparkPostResponse;
import com.dukejiang.action_send.repository.AudienceRepository;
import com.dukejiang.action_send.repository.TransmissionRepository;
import com.dukejiang.action_send.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j // logger
@RestController
@RequestMapping(path = "/actionSend")
@CrossOrigin(origins="*")
public class ActionSendController {
    private final TransmissionRepository transmissionRepository;
    private final UserRepository userRepository;
    private final AudienceRepository audienceRepository;
    @Autowired
    WebClient webClient;


    @Autowired
    public ActionSendController(TransmissionRepository transmissionRepository,
                                        UserRepository userRepository,
                                        AudienceRepository audienceRepository){
        this.transmissionRepository = transmissionRepository;
        this.userRepository = userRepository;
        this.audienceRepository = audienceRepository;
    }


    @RequestMapping(value={"/createTransmission"}, method = POST)
    @ResponseBody
    public ResponseEntity<Response> createTransmission(@RequestBody TransmissionRequest transmissionRequest){
        Optional<SparkPostResponse> sparkPostResponse = webClient.post()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .accept(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN)
                .body(Mono.just(transmissionRequest), TransmissionRequest.class)
                .retrieve()
                .bodyToMono(SparkPostResponse.class)
                .blockOptional();

        if(sparkPostResponse.isEmpty()) return ResponseEntity
                .status(HttpStatus.FAILED_DEPENDENCY).body(new Response());

        //record keeping
        Transmission transmission = new Transmission();
        transmission.setId(sparkPostResponse.get().getTransmissionId());
        transmission.setAudience_email(transmissionRequest.getAddressList().get(0).getAddress());
        transmission.setAudience(audienceRepository.getReferenceById(transmissionRequest.getAudienceId()));
        transmission.setUser(userRepository.getReferenceById(transmissionRequest.getUserId()));
        transmissionRepository.save(transmission);

        Response response = new Response();
        response.setStatusCode(200);
        response.setMsg("Transmission successfully created");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
