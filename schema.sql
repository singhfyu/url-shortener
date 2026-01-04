CREATE TABLE url_mapping (
    id BIGINT NOT NULL AUTO_INCREMENT,
    short_code VARCHAR(10) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
    long_url TEXT NOT NULL,
    click_count BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP NULL DEFAULT NULL,

    PRIMARY KEY (id),
    UNIQUE INDEX idx_short_code (short_code)
) ENGINE=InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;
