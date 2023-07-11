import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.whiteleaf03.api.WindyApiBackendApplication;
import top.whiteleaf03.api.modal.dto.EmailInfoDTO;
import top.whiteleaf03.api.service.email.EmailService;

@SpringBootTest(classes = WindyApiBackendApplication.class)
public class EmailTest {
    private final EmailService emailService;

    @Autowired
    public EmailTest(EmailService emailService) {
        this.emailService = emailService;
    }

    @Test
    public void sent() {
        emailService.sentEmail(new EmailInfoDTO("2876202234@qq.com", "欢迎新用户", "请妥善保管好您的秘钥!\n[accessKey]" + "accessKey" + "\n[secretKey]" + "secretKey"));
    }
}
