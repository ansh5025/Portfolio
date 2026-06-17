package in.anshrai.portfolio.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.anshrai.portfolio.dto.VisitorLogEntry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class VisitorLogStorageService {

    private static final ZoneId IST_ZONE = ZoneId.of("Asia/Kolkata");
    private static final DateTimeFormatter IST_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    private final ObjectMapper objectMapper;
    private final Path logFilePath;

    public VisitorLogStorageService(ObjectMapper objectMapper,
                                    @Value("${app.visitor-log.json-file:logs/landing-visits.json}") String logFilePath) {
        this.objectMapper = objectMapper;
        Path path = Path.of(logFilePath);
        if (!path.isAbsolute()) {
            path = Path.of(System.getProperty("user.dir")).resolve(path).normalize();
        }
        this.logFilePath = path;
    }

    public Path getLogFilePath() {
        return logFilePath;
    }

    public synchronized void store(String ipAddress,
                                   String osType,
                                   String deviceType,
                                   String model,
                                   Double latitude,
                                   Double longitude,
                                   Double accuracy,
                                   String locationStatus,
                                   String path) {
        List<VisitorLogEntry> entries = readEntries();

        VisitorLogEntry entry = new VisitorLogEntry();
        entry.setTimestamp(ZonedDateTime.now(IST_ZONE).format(IST_FORMATTER));
        entry.setIp(normalizeString(ipAddress));
        entry.setOs(normalizeString(osType));
        entry.setDevice(normalizeString(deviceType));
        entry.setModel(normalizeString(model));
        entry.setLatitude(latitude);
        entry.setLongitude(longitude);
        entry.setAccuracy(accuracy);
        entry.setLocationStatus(normalizeString(locationStatus));
        entry.setPath(normalizeString(path));

        entries.add(entry);
        writeEntries(entries);
    }

    public synchronized List<VisitorLogEntry> getAll() {
        return List.copyOf(readEntries());
    }

    private List<VisitorLogEntry> readEntries() {
        if (!Files.exists(logFilePath)) {
            return new ArrayList<>();
        }

        try {
            return new ArrayList<>(objectMapper.readValue(logFilePath.toFile(), new TypeReference<List<VisitorLogEntry>>() {
            }));
        } catch (IOException ex) {
            throw new IllegalStateException("Unable to read visitor log file.", ex);
        }
    }

    private void writeEntries(List<VisitorLogEntry> entries) {
        try {
            Path parent = logFilePath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(logFilePath.toFile(), entries);
        } catch (IOException ex) {
            throw new IllegalStateException("Unable to write visitor log file.", ex);
        }
    }

    private String normalizeString(String value) {
        return value == null || value.isBlank() ? "unknown" : value;
    }
}
