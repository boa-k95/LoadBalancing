package com.CHAT01.command;

import com.intesasanpaolo.bear.core.command.BaseCommand;
import com.CHAT01.command.dto.ConsoleDLPCommandInput;
import com.CHAT01.command.dto.ConsoleDLPCommandOutput;
import com.CHAT01.connector.dlp.google.token.model.ChannelEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import service.google.ContentService;

@Component
@Scope("prototype")
@RequiredArgsConstructor
public class ConsoleDLPCommand extends BaseCommand<ConsoleDLPCommandOutput> {
    @Autowired
    private ContentService contentService;

    private final ConsoleDLPCommandInput commandInput;

    @Override
    public boolean canExecute() {
        return true;
    }

    @Override
    public ConsoleDLPCommandOutput doExecute() {
        ConsoleDLPCommandOutput consoleDLPCommandOutput = new ConsoleDLPCommandOutput();
        consoleDLPCommandOutput.setOriginalText(commandInput.getText());

        logger.info("Calling DLP for content inspection on text: '{}'", commandInput.getText());
        String maskedText = contentService.getMaskedText(commandInput.getText(), ChannelEnum.APP);
        logger.info("Masked text: '{}'", maskedText);

        consoleDLPCommandOutput.setMaskedText(maskedText);
        return consoleDLPCommandOutput;
    }
}
