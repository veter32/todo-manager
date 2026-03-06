/*
 *
 *  * Personal Task Manager
 *  * Copyright (c) ${YEAR} Vitalii Yeremenko
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 *
 */

package com.evsoft.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response object for Task")
public record TaskResponse(
        @Schema(description = "ID of the task")
        Long id,

        @Schema(description = "Title of the task")
        String title,

        @Schema(description = "Completion status")
        boolean completed
) {}
