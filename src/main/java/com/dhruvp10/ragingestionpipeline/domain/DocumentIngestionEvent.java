package com.dhruvp10.ragingestionpipeline.domain;

import java.util.UUID;

public record DocumentIngestionEvent(
        UUID eventId,
        String sourceUrl,
        String content,
        String hashContent,
        Action action
) {
    public enum Action {
        UPSERT, // Create or update chunks for URL
        DELETE, // Purge all chunks for this URL (e.g., page 404ed)
    }

    public static DocumentIngestionEvent createUpsertEvent(String sourceUrl, String content, String hashContent) {
        return new DocumentIngestionEvent(UUID.randomUUID(), sourceUrl, content, hashContent, Action.UPSERT);
    }

    public static DocumentIngestionEvent createDeleteEvent(String sourceUrl) {
        return new DocumentIngestionEvent(UUID.randomUUID(), sourceUrl, null, null, Action.DELETE);
    }


}
