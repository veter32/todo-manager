# Docker Setup

This document describes how to build and run the TODO Manager server
using Docker.

The server is packaged as a Spring Boot executable JAR and deployed
using a multi-stage Docker build.

------------------------------------------------------------------------

## Prerequisites

-   Docker 20+ installed
-   Docker daemon running

Verify installation:

``` bash
docker --version
```

------------------------------------------------------------------------

## Project Structure Assumption

Docker build commands must be executed from the `server/` directory,
where the `Dockerfile` is located.

------------------------------------------------------------------------

## Build Docker Image

Build the image with a version tag:

``` bash
docker build -t todo-server:1.0 .
```

Where:

-   `todo-server` --- image name
-   `1.0` --- image version tag

Verify the image was created:

``` bash
docker images
```

------------------------------------------------------------------------

## Run Container

Start the container:

``` bash
docker run -p 8080:8080 todo-server:1.0
```

The application will be available at:

http://localhost:8080

------------------------------------------------------------------------

## Run in Detached Mode

Run the container in background:

``` bash
docker run -d -p 8080:8080 --name todo-server todo-server:1.0
```

Check running containers:

``` bash
docker ps
```

Stop container:

``` bash
docker stop todo-server
```

Remove container:

``` bash
docker rm todo-server
```

------------------------------------------------------------------------

## Rebuilding the Image

If source code changes, rebuild the image:

``` bash
docker build -t todo-server:1.1 .
```

It is recommended to increment the version tag instead of reusing
`latest`.

------------------------------------------------------------------------

## Exposed Port

The application exposes port `8080`.

If needed, you can map it to a different host port:

``` bash
docker run -p 9090:8080 todo-server:1.0
```

The application will then be available at: http://localhost:9090

