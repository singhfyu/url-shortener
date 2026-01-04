# URL Shortener Backend

## Features
- Short URL generation using Base62 encoding
- Collision-safe short code creation using DB unique constraints
- Cache-aside pattern for read-heavy redirects
- TTL-based URL expiry enforced at database level
- Concurrency-safe click analytics using atomic updates

## Tech Stack
- Java
- MySQL
- JDBC
- In-memory cache (ConcurrentHashMap)

## Design
- Clean layered architecture (Handler → Service → Repository)
- Database as source of truth
- Cache used only as performance optimization