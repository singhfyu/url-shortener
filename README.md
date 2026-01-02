# URL Shortener (Core Java)

A lightweight URL shortener built using **pure Java** without Spring Boot or any database.  
This project focuses on **Low Level Design (LLD)**, HTTP fundamentals, and clean architecture.

---

## Features
- Generate short URLs for long URLs
- Redirect short URLs to original long URLs
- In-memory storage using Map
- UUID-based ID generation with Base62 encoding
- Custom HTTP server using `com.sun.net.httpserver.HttpServer`

---

## Tech Stack
- Java 21
- Java HttpServer
- No frameworks (no Spring)
- No database (in-memory storage)

---

## Design Overview

| Layer | Responsibility |
|-------|----------------|
| `HttpHandler` | Handles HTTP requests |
| `UrlService` | Business logic |
| `UrlRepository` | Storage abstraction (interface) |
| `InMemoryUrlRepository` | Map-based storage |
| `ShortCodeGenerator` | UUID + Base62 encoding |
| `App` | Application entry point |

---

## API Endpoints

### Create Short URL
`POST /shorten`  
Body: long URL (plain text)

### Redirect
`GET /{shortCode}`

---

## Why no Spring / DB?
This project intentionally avoids frameworks to demonstrate:
- HTTP request/response handling
- LLD principles
- Clean separation of concerns

A Spring Boot version with persistence is planned as a future enhancement.

---

## How to Run
1. Clone the repository
2. Open in IntelliJ
3. Run `App.java`
4. Server starts on port `8080`

---

## Future Improvements
- **Spring Boot implementation**: Replace the custom HttpServer with Spring Boot to leverage built-in routing, dependency injection, and REST support.
- **Persistent storage (MySQL / Redis)**: Use a database instead of in-memory Map to store URL mappings across server restarts.
- **Collision handling**: Ensure generated short codes are unique by checking the repository before saving.
- **Analytics (click count)**: Track how many times each short URL is accessed.

### Collision Handling Approach

Short codes are generated using 64-bit UUIDs and Base62 encoding. While the probability of collision is extremely low (2^64 possible values), it is theoretically possible. To ensure uniqueness:

1. Generate a candidate short code (UUID → long → Base62).
2. Check repository: `while (repository.exists(shortCode))`.
3. If it exists, generate a new code and repeat until unique.
4. Store the code in the repository.

> With 2^64 possible codes, this loop almost always executes only once.
