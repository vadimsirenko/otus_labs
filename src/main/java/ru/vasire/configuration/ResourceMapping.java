package ru.vasire.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceMapping {
    @JsonProperty("address")
    private String address;
    @JsonProperty("path")
    private String path;
}
