package io.github.alpen.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Executors;

public class Socket {

    private AsynchronousServerSocketChannel asyncSocket;
    private List<Client> connections = new Vector<>();
    public Socket(){

    }
    public void start() throws IOException {

        AsynchronousChannelGroup channelGroup = null;
        channelGroup = AsynchronousChannelGroup.withFixedThreadPool(

                Runtime.getRuntime().availableProcessors(),

                Executors.defaultThreadFactory()

        );

        asyncSocket = AsynchronousServerSocketChannel.open(channelGroup);
        asyncSocket.bind(new InetSocketAddress(5009));

        asyncSocket.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {

            @Override
            public void completed(AsynchronousSocketChannel result, Void attachment) {

                Client client = new Client(result, connections);
                connections.add(client);
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                stop();
            }

        });


    }


    public void stop() {
        connections.clear();
        try {
            asyncSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
