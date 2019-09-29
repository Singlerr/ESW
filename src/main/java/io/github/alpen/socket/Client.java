package io.github.alpen.socket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.List;

public class Client {

    private AsynchronousSocketChannel socketChannel;
    private List<Client> connections;

    public Client(AsynchronousSocketChannel socketChannel, List<Client> connections) {
        this.socketChannel = socketChannel;
        this.connections = connections;
        receive();
    }

    private void receive() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);

        final Client client = this;

        socketChannel.read(byteBuffer, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {

            @Override

            public void completed(Integer result, ByteBuffer attachment) {

                try {
                    attachment.flip();
                    Charset charset = Charset.forName("utf-8");
                    String data = charset.decode(attachment).toString();

                    ReceiveString receiveString = new ReceiveString(data, client);
                    receiveString.execute();

                    ByteBuffer byteBuffer = ByteBuffer.allocate(100);
                    socketChannel.read(byteBuffer, byteBuffer, this);
                } catch(Exception e) {}
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                try {
                    connections.remove(Client.this);
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        });

    }




    public void send(String data) {

        Charset charset = Charset.forName("utf-8");

        ByteBuffer byteBuffer = charset.encode(data);


        socketChannel.write(byteBuffer, null, new CompletionHandler<Integer, Void>() {

            @Override

            public void completed(Integer result, Void attachment) {
            }

            @Override

            public void failed(Throwable exc, Void attachment) {

                try {
                    connections.remove(Client.this);
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
