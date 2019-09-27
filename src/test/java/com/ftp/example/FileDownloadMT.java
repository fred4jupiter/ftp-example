package com.ftp.example;

import org.junit.Test;
import org.springframework.http.*;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class FileDownloadMT {

    @Test
    public void downloadFile() throws IOException {
        final String url = "https://live.staticflickr.com/4310/35483393683_9ec4ac4ff8_m_d.jpg";

        RestTemplate restTemplate = new RestTemplate(Collections.singletonList(new ByteArrayHttpMessageConverter()));

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class);
        assertThat(response).isNotNull();

        if (response.getStatusCode() == HttpStatus.OK) {
            Path path = Paths.get("target/test.jpg");
            byte[] fileAsBinary = response.getBody();
            assertThat(fileAsBinary).isNotNull().isNotEmpty();
            Files.write(path, fileAsBinary);
            assertThat(path).exists();
        }
    }
}
