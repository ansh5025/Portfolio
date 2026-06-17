package in.anshrai.portfolio;

import in.anshrai.portfolio.service.ResendEmailSender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
        "app.contact.mail.api-key=test-api-key",
        "app.contact.mail.from=Portfolio <onboarding@resend.dev>",
        "app.contact.mail.to=test@example.com",
        "app.visitor-log.json-file=target/test-logs/landing-visits.json"
})
@AutoConfigureMockMvc
@ExtendWith(OutputCaptureExtension.class)
class PortfolioApplicationTests {

    private static final Path TEST_LOG_FILE = Path.of("target/test-logs/landing-visits.json");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResendEmailSender resendEmailSender;

    @AfterEach
    void cleanupLogFile() throws Exception {
        Files.deleteIfExists(TEST_LOG_FILE);
    }

    @Test
    void contextLoads() {
    }

    @Test
    void doesNotLogRegularApiRequests(CapturedOutput output) throws Exception {
        mockMvc.perform(get("/api/profile")
                        .header("X-Forwarded-For", "203.0.113.10")
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"))
                .andExpect(status().isOk());

        assertThat(output.getOut()).doesNotContain("Path=/api/profile");
    }

    @Test
    void logsLandingVisitor(CapturedOutput output) throws Exception {
        mockMvc.perform(post("/api/visits/landing")
                        .contentType("application/json")
                        .content("{\"model\":\"SM-S928B\",\"latitude\":18.52043,\"longitude\":73.856743,\"accuracy\":12.0,\"locationStatus\":\"granted\"}")
                        .header("X-Forwarded-For", "198.51.100.24")
                        .header("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 17_0 like Mac OS X)"))
                .andExpect(status().isOk());

        assertThat(output.getOut()).contains("Visitor IP=198.51.100.24");
        assertThat(output.getOut()).contains("OS=iOS");
        assertThat(output.getOut()).contains("Device=Mobile");
        assertThat(output.getOut()).contains("Model=SM-S928B");
        assertThat(output.getOut()).contains("Latitude=18.52043");
        assertThat(output.getOut()).contains("Longitude=73.856743");
        assertThat(output.getOut()).contains("Accuracy=12.0");
        assertThat(output.getOut()).contains("LocationStatus=granted");
        assertThat(output.getOut()).contains("Path=/api/visits/landing");
    }

    @Test
    void storesLandingVisitorAsJsonInIst() throws Exception {
        mockMvc.perform(post("/api/visits/landing")
                        .contentType("application/json")
                        .content("{\"model\":\"RMX5085\",\"latitude\":18.6048084,\"longitude\":73.7314831,\"accuracy\":17.52899932861328,\"locationStatus\":\"granted\"}")
                        .header("X-Forwarded-For", "45.250.227.150:50504")
                        .header("User-Agent", "Mozilla/5.0 (Linux; Android 14; RMX5085)"))
                .andExpect(status().isOk());

        assertThat(TEST_LOG_FILE).exists();
        String fileContent = Files.readString(TEST_LOG_FILE);

        assertThat(fileContent).contains("\"ip\" : \"45.250.227.150\"");
        assertThat(fileContent).contains("\"os\" : \"Android\"");
        assertThat(fileContent).contains("\"device\" : \"Mobile\"");
        assertThat(fileContent).contains("\"model\" : \"RMX5085\"");
        assertThat(fileContent).contains("\"latitude\" : 18.6048084");
        assertThat(fileContent).contains("\"longitude\" : 73.7314831");
        assertThat(fileContent).contains("\"accuracy\" : 17.52899932861328");
        assertThat(fileContent).contains("\"locationStatus\" : \"granted\"");
        assertThat(fileContent).contains("\"path\" : \"/api/visits/landing\"");
        assertThat(fileContent).contains("+05:30");
    }

    @Test
    void exposesLandingLogsEndpoint() throws Exception {
        mockMvc.perform(post("/api/visits/landing")
                        .contentType("application/json")
                        .content("{\"model\":\"RMX5085\",\"latitude\":18.6048084,\"longitude\":73.7314831,\"accuracy\":17.52899932861328,\"locationStatus\":\"granted\"}")
                        .header("X-Forwarded-For", "45.250.227.150")
                        .header("User-Agent", "Mozilla/5.0 (Linux; Android 14; RMX5085)"))
                .andExpect(status().isOk());

        // Logs download endpoint requires X-Visitor-Log-Secret header and returns file
        mockMvc.perform(get("/api/visits/landing/logs/download")
                        .header("X-Visitor-Log-Secret", "test-secret"))
                .andExpect(status().isUnauthorized()); // Will be 401 without matching secret
    }

    @Test
    void sendsContactEmail() throws Exception {
        mockMvc.perform(post("/api/contact")
                        .contentType("application/json")
                        .content("""
                                {
                                  "name": "Ansh Rai",
                                  "email": "sender@example.com",
                                  "subject": "Hello",
                                  "message": "Testing contact email delivery."
                                }
                                """))
                .andExpect(status().isCreated());

        verify(resendEmailSender).send(any());
    }
}
