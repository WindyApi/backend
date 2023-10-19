package top.whiteleaf03.api.modal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author WhiteLeaf03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "interface_invoke_record")
public class InterfaceInvokeRecordDTO {
    /**
     * 请求id
     */
    private String id;

    /**
     * 请求来源
     */
    private String remoteAddress;

    /**
     * 请求方式
     */
    private String method;

    /**
     * 请求路径
     */
    private String requestPath;

    /**
     * 携带时间戳
     */
    private Long timestamp;

    /**
     * 当前时间戳
     */
    private Long currentTimestamp;

    /**
     * 请求头
     */
    private org.bson.Document requestHeader;

    /**
     * 请求参数
     */
    private org.bson.Document requestParams;

    /**
     * 请求体
     */
    private org.bson.Document body;

    /**
     * 用户ak
     */
    private String accessKey;

    /**
     * 用户签名
     */
    private String sign;

    /**
     * 请求耗时
     */
    private Long useTime;

    /**
     * 是否通过
     */
    private Boolean accept;

    /**
     * 失败原因
     */
    private String msg;
}
