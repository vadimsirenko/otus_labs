package ru.vasire;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vasire.configuration.ResourceMapping;
import ru.vasire.configuration.ServerConfiguration;
import ru.vasire.configuration.ServerConfigurationLoader;

import java.io.*;
import java.net.Socket;
import java.util.Optional;

@RequiredArgsConstructor
public class HttpProcessingTask implements Runnable {
    private final Socket socket;

    private static String CONTENT_TYPE_TEXT = "Content-Type: text/html; charset=utf-8";
    private static String CONTENT_TYPE_JPEG = "image/jpeg";

    private String contentType = CONTENT_TYPE_TEXT;
    private final ServerConfiguration serverConfiguration;
    private static final Logger log = LoggerFactory.getLogger(ServerConfigurationLoader.class);

    @Override
    public void run() {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             OutputStream output = socket.getOutputStream()) {
            try {
                InputStream resourceStream = findResource(input);
                if (resourceStream != null) {
                    writeResponse(output, "200 OK", resourceStream.readAllBytes());
                    resourceStream.close();
                } else {
                    writeResponse(output, "404 Not Found", "<h1>Resource not found :(</h1>".getBytes());
                }
            } catch (Exception ex) {
                writeResponse(output, "500 HTTP/1.0 Internal Server Error", "<h1>The request was not completed due to an internal error on the server side. :(</h1>".getBytes());
            }
            output.flush();
            socket.close();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void writeResponse(OutputStream output, String status, byte[] content) throws IOException {
        output.write(("HTTP/1.1 " + status).getBytes());
        output.write("\r\n".getBytes());
        output.write(contentType.getBytes());
        output.write("\r\n".getBytes());
        output.write("\r\n".getBytes());
        output.write(content);
        output.write("\r\n".getBytes());
    }

    private InputStream findResource(BufferedReader input) throws IOException {
        InputStream resourceStream = null;
        final String address = findAddress(input);
        if ((address != null) && (address.endsWith(".html") || address.endsWith(".jpg"))) {
            String fileResource = serverConfiguration.getMappings().stream()
                    .filter(m -> m.getAddress().equals(address))
                    .map(ResourceMapping::getPath)
                    .findFirst().orElse(null);
            if (fileResource != null) {
                setContentType(fileResource);
                resourceStream = getClass().getResourceAsStream(fileResource);
            }
        }
        return resourceStream;
    }

    private void setContentType(String fileResource) {
        Optional<String> extFile = getExtensionByStringHandling(fileResource);
        if (extFile.isPresent()) {
            switch (extFile.get()) {
                case "jpeg" -> contentType = CONTENT_TYPE_JPEG;
                case "jpg" -> contentType = CONTENT_TYPE_JPEG;
                default -> contentType = CONTENT_TYPE_TEXT;
            }
        }
    }

    private static String findAddress(BufferedReader in) throws IOException {
        String s;
        while ((s = in.readLine()) != null || s.isEmpty()) {
            if (s.startsWith("GET")) return s.split(" ")[1];
        }
        return null;
    }

    public static Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1).toLowerCase());
    }
}