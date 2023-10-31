package top.whiteleaf03.api.modal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

/**
 * @author WhiteLeaf03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvokeApiDTO {
    /**
     * 接口id
     */
    private Long interfaceId;

    /**
     * GET请求参数
     */
    private HashMap<String, Object> paramsJson;

    /**
     * POST请求参数
     */
    private HashMap<String, Object> dataJson;
}
