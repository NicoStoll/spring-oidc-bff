package de.deutscherv.gq0500.springbff.resource;

import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContentService {

    List<ContentDto> messages = new ArrayList<>();

    public @Nullable List<ContentDto> getMessages() {
        return messages;
    }

    public @Nullable ContentDto createMessage(ContentDto contentDto) {
        messages.add(contentDto);
        return contentDto;
    }
}
