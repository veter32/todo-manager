package com.evsoft.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record TaskRequest(
        @NotBlank(message = "The title cannot be empty")
        @Size(min = 3, max = 100, message = "The title should have 3-100 symbols")
        String title,
        boolean completed
) {}
