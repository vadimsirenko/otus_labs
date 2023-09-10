package ru.vasire.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.util.List;

@Getter
@Setter
public class ServerConfiguration {
    @JsonProperty("port")
    private int port;
    @JsonProperty("mappings")
    private List<ResourceMapping> mappings;
}
