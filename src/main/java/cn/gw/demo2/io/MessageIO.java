package cn.gw.demo2.io;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MessageIO {

    String EXCHANGE = "event_one";

    String EVENT_ONE_PRODUCER = EXCHANGE + "_producer";

    String EVENT_ONE_CONSUMER = EXCHANGE + "_consumer";

    @Input(EVENT_ONE_CONSUMER)
    MessageChannel input();

    @Output(EVENT_ONE_PRODUCER)
    MessageChannel output();
}
