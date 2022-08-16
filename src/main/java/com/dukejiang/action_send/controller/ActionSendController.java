package com.dukejiang.action_send.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j // logger
@RestController
@RequestMapping(path = "/action_send")
@CrossOrigin(origins="*")
public class ActionSendController {
}
