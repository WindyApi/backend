package top.whiteleaf03.api.modal.vo;

import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.whiteleaf03.api.modal.document.InterfaceInfoDocument;
import top.whiteleaf03.api.modal.entity.InterfaceInfo;

import java.util.Date;

/**
 * @author WhiteLeaf03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterfaceInfoVO {
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
     * 创建人昵称
     */
    private String nickname;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    public InterfaceInfoVO(InterfaceInfo interfaceInfo, String nickname, InterfaceInfoDocument interfaceInfoDocument) {
        this.id = interfaceInfo.getId();
        this.name = interfaceInfo.getName();
        this.describe = interfaceInfo.getDescribe();
        this.method = interfaceInfo.getMethod();
        this.url = interfaceInfo.getUrl();
        this.params = JSONUtil.toJsonStr(interfaceInfoDocument.getParams());
        this.requestHeader = JSONUtil.toJsonStr(interfaceInfoDocument.getRequestHeader());
        this.responseHeader = JSONUtil.toJsonStr(interfaceInfoDocument.getResponseHeader());
        this.status = interfaceInfo.getStatus();
        this.nickname = nickname;
        this.createTime = interfaceInfo.getCreateTime();
        this.updateTime = interfaceInfo.getUpdateTime();
    }
}
