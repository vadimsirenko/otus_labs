package ru.vasire.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ServerConfigurationLoader {

    private static final Logger log = LoggerFactory.getLogger(ServerConfigurationLoader.class);
    public static ServerConfiguration load(){
        ServerConfiguration serverConfiguration;

        try (BufferedReader br = new BufferedReader(new FileReader(ServerConfigurationLoader.class.getResource("/app.json").getPath(), StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            ObjectMapper om = new ObjectMapper();
            serverConfiguration = om.readValue(sb.toString(), ServerConfiguration.class);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return serverConfiguration;
    }
}
