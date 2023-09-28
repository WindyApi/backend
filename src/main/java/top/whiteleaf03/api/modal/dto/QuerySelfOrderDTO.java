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
public class QuerySelfOrderDTO {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 页号
     */
    private Integer pageNum;
}
