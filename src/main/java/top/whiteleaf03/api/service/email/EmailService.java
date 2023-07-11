package top.whiteleaf03.api.service.email;

import top.whiteleaf03.api.modal.dto.EmailInfoDTO;

/**
 * @author WhiteLeaf03
 */
public interface EmailService {
    /**
     * 发送邮件
     *
     * @param emailInfoDTO 邮件信息
     */
    void sentEmail(EmailInfoDTO emailInfoDTO);
}
