package com.dukejiang.action_send.controller;

import com.dukejiang.action_send.model.Transmission;
import com.dukejiang.action_send.model.request.ScheduledTransmissionRequest;
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

import java.time.LocalDateTime;
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
    private final WebClient webClient;



    @Autowired
    public ActionSendController(TransmissionRepository transmissionRepository,
                                        UserRepository userRepository,
                                        AudienceRepository audienceRepository,
                                        WebClient webClient){
        this.transmissionRepository = transmissionRepository;
        this.userRepository = userRepository;
        this.audienceRepository = audienceRepository;
        this.webClient = webClient;
    }


    @RequestMapping(value={"/createTransmission"}, method = POST)
    @ResponseBody
    public ResponseEntity<Response> createTransmission(@RequestBody TransmissionRequest transmissionRequest){
        Optional<SparkPostResponse> sparkPostResponse = webClient.post()
                .uri("/api/v1/transmissions?num_rcpt_errors=3")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(transmissionRequest), TransmissionRequest.class)
                .retrieve()
                .bodyToMono(SparkPostResponse.class)
                .blockOptional();

        if(sparkPostResponse.isEmpty()) return ResponseEntity
                .status(HttpStatus.FAILED_DEPENDENCY).body(new Response());

        //record keeping
        Transmission transmission = new Transmission();
        log.info("transmission id is" + sparkPostResponse.get().getSparkPostResults().getTransmissionId());
        transmission.setId(sparkPostResponse.get().getSparkPostResults().getTransmissionId());
        transmission.setAudience_email(transmissionRequest.getAddressList().get(0).getAddress());
        transmission.setAudience(audienceRepository.getReferenceById(transmissionRequest.getAudienceId()));
        transmission.setUser(userRepository.getReferenceById(transmissionRequest.getUserId()));
        transmission.setCreatedAt(LocalDateTime.now());
        transmission.setCreatedBy("" + transmissionRequest.getUserId());
        transmissionRepository.save(transmission);

        Response response = new Response();
        response.setStatusCode(200);
        response.setMsg("Transmission successfully created");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @RequestMapping(value={"/createScheduledTransmission"}, method = POST)
    @ResponseBody
    public ResponseEntity<Response> createScheduleTransmission(
                                @RequestBody ScheduledTransmissionRequest scheduledTransmissionRequest){
        Optional<SparkPostResponse> sparkPostResponse = webClient.post()
                .uri("/api/v1/transmissions?num_rcpt_errors=3")
//                .header("Content-Type", "application/json")
//                .header("Accept", "application/json")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(scheduledTransmissionRequest), ScheduledTransmissionRequest.class)
                .retrieve()
                .bodyToMono(SparkPostResponse.class)
                .blockOptional();

        if(sparkPostResponse.isEmpty()) return ResponseEntity
                .status(HttpStatus.FAILED_DEPENDENCY).body(new Response());

        //record keeping
        Transmission transmission = new Transmission();
        log.info("transmission id is" + sparkPostResponse.get().getSparkPostResults().getTransmissionId());
        transmission.setId(sparkPostResponse.get().getSparkPostResults().getTransmissionId());
        transmission.setAudience_email(scheduledTransmissionRequest.getAddressList().get(0).getAddress());
        transmission.setAudience(audienceRepository.getReferenceById(scheduledTransmissionRequest.getAudienceId()));
        transmission.setUser(userRepository.getReferenceById(scheduledTransmissionRequest.getUserId()));
        transmission.setCreatedAt(LocalDateTime.now());
        transmission.setCreatedBy("" + scheduledTransmissionRequest.getUserId());
        transmissionRepository.save(transmission);

        Response response = new Response();
        response.setStatusCode(200);
        response.setMsg("Scheduled Transmission successfully created");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}

