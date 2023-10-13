package top.whiteleaf03.api.modal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author WhiteLeaf03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessKeyDTO implements Serializable {
    /**
     * 用户的ak
     */
    private String accessKey;
}
