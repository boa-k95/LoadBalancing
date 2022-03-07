package command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface  BaseCommand<R> {
    static final Logger logger = LoggerFactory.getLogger(BaseCommand.class);
  public R execute();
}
