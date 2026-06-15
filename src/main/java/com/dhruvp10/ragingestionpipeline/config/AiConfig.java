package com.dhruvp10.ragingestionpipeline.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {
    private String ollamaBaseUrl;
    private String modelName;
    private String dataSourceUrl;
    private String dbUsername;
    private String dbPassword;

    @Bean
    public EmbeddingModel embeddingModel() {
        return OllamaEmbeddingModel.builder()
                .baseUrl(ollamaBaseUrl)
                .modelName(modelName)
                .build();
    }

    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {
        // Extracts host, port, and DB name from the JDBC URL
        // (jdbc:postgresql://localhost:5432/vectordb)
        String cleanUrl = dataSourceUrl.replace("jdbc:postgresql://", "");
        String[] parts = cleanUrl.split("/");
        String hostAndPort = parts[0];
        String database = parts[1];

        String[] hostPortParts = hostAndPort.split(":");
        String host = hostPortParts[0];
        Integer port = Integer.parseInt(hostPortParts[1]);

        return PgVectorEmbeddingStore.builder()
                .host(host)
                .port(port)
                .database(database)
                .user(dbUsername)
                .password(dbPassword)
                .table("document_chunks")
                .dimension(1536)
                .build();
    }
}
