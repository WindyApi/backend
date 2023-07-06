package top.whiteleaf03.api.modal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WhiteLeaf03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInterfaceStatusDTO {
    /**
     * 接口id
     */
    private Long id;

    /**
     * 接口状态
     * 0 下线
     * 1 上线
     */
    private Boolean status;
}
