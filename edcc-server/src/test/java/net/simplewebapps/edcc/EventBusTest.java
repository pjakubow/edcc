package net.simplewebapps.edcc;

import net.simplewebapps.edcc.event.ClearSavedGame;
import net.simplewebapps.edcc.event.Fileheader;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class EventBusTest {

    @Test
    public void shouldNotifyWhenAcceptableEvent() throws Exception {

        // given
        EventSubscriber eventSubscriber = mock(EventSubscriber.class);
        when(eventSubscriber.accepts(ClearSavedGame.class)).thenReturn(true);

        EventBus eventBus = new EventBus();
        eventBus.addSubscriber(eventSubscriber);

        // when
        eventBus.publish(new ClearSavedGame());

        // then
        verify(eventSubscriber).onEvent(any(ClearSavedGame.class));
    }

    @Test
    public void shouldNotNotifyWhenUnacceptableEvent() throws Exception {

        // given
        EventSubscriber eventSubscriber = mock(EventSubscriber.class);
        when(eventSubscriber.accepts(Fileheader.class)).thenReturn(false);

        EventBus eventBus = new EventBus();
        eventBus.addSubscriber(eventSubscriber);

        // when
        eventBus.publish(new Fileheader());

        // then
        verify(eventSubscriber, never()).onEvent(any(Fileheader.class));
    }
}