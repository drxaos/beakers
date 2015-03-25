package beakers.auth.websocket

import javax.websocket.*

interface Endpoint {

    @OnOpen
    void onOpenHandler(final Session session);

    @OnClose
    void onCloseHandler(final Session session)

    @OnMessage
    void onMessageHandler(final Session session, String payload)

    @OnError
    public void onError(final Session session, Throwable t)
}