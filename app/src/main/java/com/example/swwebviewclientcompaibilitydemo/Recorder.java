package com.example.swwebviewclientcompaibilitydemo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/***
 * Records events to be replayed later. We use this because by the event comes, it may not be possible
 * to display it on the screen.
 */
public class Recorder {
    private List<String> messages;

    public Recorder() {
        this.messages = new ArrayList<>();
    }

    public void addMessage(String message) {
        messages.add(message);
    }

    public void reset() {
        messages.clear();
    }

    public List<String> getAllRecordedMessages() {
        return Collections.unmodifiableList(messages);
    }
}
