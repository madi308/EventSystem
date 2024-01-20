package com.practice.event.EventSystem.event;

import com.practice.event.EventSystem.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/event")
public class EventController {

    private final EventService eventService;


    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<Event> getEvents() {
        return eventService.getEvents();
    }

    @GetMapping(path = "{eventId}")
    public Event getEventById(@PathVariable Long eventId) {
        return eventService.getEventById(eventId);
    }

    @GetMapping(path = "{eventName}")
    public List<Event> getEventByName(@PathVariable String eventName) {
        return eventService.getEventsByName(eventName);
    }

    @PostMapping
    public void registerNewEvent(@RequestBody Event event) {
        eventService.addEvent(event);
    }

    @DeleteMapping(path = "{eventId}")
    public void deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
    }

    @PutMapping(path = "/addParticipants/{eventId}")
    public void addParticipants(@PathVariable Long eventId, @RequestParam Long userId) {
        eventService.addParticipants(eventId, userId);
    }
}
