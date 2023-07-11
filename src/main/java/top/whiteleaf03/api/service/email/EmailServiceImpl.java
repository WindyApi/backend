package top.whiteleaf03.api.service.email;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import top.whiteleaf03.api.modal.dto.EmailInfoDTO;

/**
 * @author WhiteLeaf03
 */
@Slf4j
@Service
public class EmailServiceImpl implements EmailService {
    private final RabbitTemplate rabbitTemplate;

    public EmailServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * 发送邮件
     *
     * @param emailInfoDTO 邮件信息
     */
    @Override
    public void sentEmail(EmailInfoDTO emailInfoDTO) {
        String emailInfoStr = JSONUtil.toJsonStr(emailInfoDTO);
        rabbitTemplate.convertAndSend("email", emailInfoStr);
    }
}
