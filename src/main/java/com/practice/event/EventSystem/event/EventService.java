package com.practice.event.EventSystem.event;

import com.practice.event.EventSystem.models.Event;
import com.practice.event.EventSystem.models.User;
import com.practice.event.EventSystem.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserService userService;

    @Autowired
    public EventService(EventRepository eventRepository, UserService userService) {
        this.eventRepository = eventRepository;
        this.userService = userService;
    }

    public List<Event> getEvents() {
        return eventRepository.findAll();
    }

    public List<Event> getEventsByName(String name) {
        return eventRepository.findByName(name);
    }

    public Event getEventById(Long id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isEmpty()) throw new IllegalArgumentException("Event with specified ID not found: " + id);
        return optionalEvent.get();
    }

    public void addEvent(Event event) {
        Optional<Event> optionalEvent = eventRepository.findById(event.getId());
        if (optionalEvent.isPresent()) throw new IllegalStateException("Event already exists");
        eventRepository.save(event);
    }

    public void deleteEvent(Long eventId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        eventOptional.ifPresent(eventRepository::delete);
    }

    public void addParticipants(Long eventId, Long userId) {
        Event event = getEventById(eventId);
        User user = userService.getUser(userId);
        event.getParticipants().add(user);
        eventRepository.save(event);
    }
}
