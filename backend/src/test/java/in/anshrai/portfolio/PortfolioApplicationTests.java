package in.anshrai.portfolio;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(OutputCaptureExtension.class)
class PortfolioApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void logsApiVisitor(CapturedOutput output) throws Exception {
        mockMvc.perform(get("/api/profile")
                        .header("X-Forwarded-For", "203.0.113.10")
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"))
                .andExpect(status().isOk());

        assertThat(output.getOut()).contains("Visitor IP=203.0.113.10");
        assertThat(output.getOut()).contains("OS=Windows");
        assertThat(output.getOut()).contains("Device=Desktop");
        assertThat(output.getOut()).contains("Model=unknown");
        assertThat(output.getOut()).contains("Path=/api/profile");
    }

    @Test
    void logsLandingVisitor(CapturedOutput output) throws Exception {
        mockMvc.perform(post("/api/visits/landing")
                        .contentType("application/json")
                        .content("{\"model\":\"SM-S928B\"}")
                        .header("X-Forwarded-For", "198.51.100.24")
                        .header("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 17_0 like Mac OS X)"))
                .andExpect(status().isOk());

        assertThat(output.getOut()).contains("Visitor IP=198.51.100.24");
        assertThat(output.getOut()).contains("OS=iOS");
        assertThat(output.getOut()).contains("Device=Mobile");
        assertThat(output.getOut()).contains("Model=SM-S928B");
        assertThat(output.getOut()).contains("Path=/api/visits/landing");
    }
}
