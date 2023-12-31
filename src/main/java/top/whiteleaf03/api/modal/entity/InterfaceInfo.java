package top.whiteleaf03.api.modal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author WhiteLeaf03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterfaceInfo {
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
    private String params;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响应头
     */
    private String responseHeader;

    /**
     * 接口状态 0 下线 1 在线
     */
    private Boolean status;

    /**
     * 创建人id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Boolean isDelete;
}
