package com.intesasanpaolo.bear.dcci0.chatbot.pulse.controller.testconsole;

import com.intesasanpaolo.bear.core.controller.StatelessController;
import com.intesasanpaolo.bear.dcci0.chatbot.pulse.command.ConsoleDLPCommand;
import com.intesasanpaolo.bear.dcci0.chatbot.pulse.command.dto.ConsoleDLPCommandInput;
import com.intesasanpaolo.bear.dcci0.chatbot.pulse.command.dto.ConsoleDLPCommandOutput;
import com.intesasanpaolo.bear.dcci0.chatbot.pulse.controller.testconsole.dto.ConsoleDLPRequest;
import com.intesasanpaolo.bear.dcci0.chatbot.pulse.controller.testconsole.dto.ConsoleDLPResponse;
import com.intesasanpaolo.bear.dcci0.chatbot.pulse.factory.IntentControllerFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/* Ad uso e consumo esclusivo della test console */
@RestController
@RequestMapping(value = "/console")
@Api(value = "console")
public class ConsoleDLPController extends StatelessController {

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private IntentControllerFactory factory;

    @PostMapping(value = "dlp")
    @ApiOperation(value = "Mask text with DLP call", consumes = "application/json")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Server error")
    })
    public ResponseEntity<ConsoleDLPResponse> callDLP(@Valid @RequestBody ConsoleDLPRequest request) throws Exception {
        ConsoleDLPCommandInput commandInput = factory.createConsoleDLPCommandInput(request);
        ConsoleDLPCommand command = beanFactory.getBean(ConsoleDLPCommand.class, commandInput);
        ConsoleDLPCommandOutput commandOutput = command.execute();
        ConsoleDLPResponse response = factory.createDetectIntentResponse(commandOutput);
        return ResponseEntity.ok(response);
    }
}
