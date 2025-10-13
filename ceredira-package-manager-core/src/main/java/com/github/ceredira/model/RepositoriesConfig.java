package com.github.ceredira.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class RepositoriesConfig {
    @JsonProperty("repositories")
    private List<Repository> repositories = new ArrayList<>();
}
