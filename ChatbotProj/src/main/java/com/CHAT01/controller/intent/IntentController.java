package com.CHAT01.controller;

import com.intesasanpaolo.bear.core.controller.StatelessController;
import com.CHAT01.command.DetectIntentCommand;
import com.CHAT01.command.dto.DetectIntentCommandInput;
import com.CHAT01.command.dto.DetectIntentCommandOutput;
import com.CHAT01.controller.intent.dto.DetectRequest;
import com.CHAT01.controller.intent.dto.DetectResponse;
import com.CHAT01.factory.IntentControllerFactory;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/intent")
public class IntentController extends StatelessController {

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private IntentControllerFactory factory;

    @PostMapping(value = "/detect")
    @ApiOperation(value = "Detect a  intent.", notes = "Detect a  intent.", consumes = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 400, message = "Bad request"), @ApiResponse(code = 500, message = "Server error")})
    public ResponseEntity<DetectResponse> detect(@Validated @RequestBody DetectRequest request) throws Exception {
        DetectIntentCommandInput input = factory.createDetectIntentCommandInput(request);
        DetectIntentCommand command = beanFactory.getBean(DetectIntentCommand.class, input);
        DetectIntentCommandOutput output = command.execute();
        DetectResponse response = factory.createDetectResponse(output);
        return ResponseEntity.ok(response);
    }

}
