package top.whiteleaf03.api.modal.dto;

import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.Document;

/**
 * @author WhiteLeaf03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInterfaceDTO {
    /**
     * id
     */
    private Long id;

    /**
     * 接口名称
     */
    private String name;

    /**
     * 接口描述
     */
    private String describe;

    /**
     * 请求类型
     */
    private String method;

    /**
     * 接口地址
     */
    private String url;

    /**
     * 请求参数
     */
    private Document params;

    /**
     * 请求头
     */
    private Document requestHeader;

    /**
     * 响应头
     */
    private Document responseHeader;

    public void setParams(String params) {
        this.params = JSONUtil.toBean(params, Document.class);
    }

    public void setRequestHeader(String requestHeader) {
        this.requestHeader = JSONUtil.toBean(requestHeader, Document.class);
    }

    public void setResponseHeader(String responseHeader) {
        this.responseHeader = JSONUtil.toBean(responseHeader, Document.class);
    }
}
