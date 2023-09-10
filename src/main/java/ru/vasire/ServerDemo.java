package ru.vasire;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vasire.configuration.ServerConfiguration;
import ru.vasire.configuration.ServerConfigurationLoader;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerDemo {
    private static final Logger log = LoggerFactory.getLogger(ServerDemo.class);

    public ServerDemo() {

        try {
            ServerConfiguration config = ServerConfigurationLoader.load();
            if (config != null && config.getPort() != 0) {
                try (ServerSocket server = new ServerSocket(config.getPort());
                     ExecutorService executorService = Executors.newCachedThreadPool()) {
                    while (true) {
                        Socket socket = server.accept();
                        executorService.submit(new HttpProcessingTask(socket, config));
                    }
                }
            } else {
                throw new RuntimeException("Failed to load configuration file");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
