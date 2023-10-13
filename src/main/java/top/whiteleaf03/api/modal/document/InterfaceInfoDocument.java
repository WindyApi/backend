package top.whiteleaf03.api.modal.document;

import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import top.whiteleaf03.api.modal.dto.NewInterfaceDTO;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "interface_info")
public class InterfaceInfoDocument {
    /**
     * 文档id
     */
    @MongoId
    private String id;

    /**
     * 接口id
     */
    private Long interfaceInfoId;

    /**
     * 请求参数
     */
    private org.bson.Document params;

    /**
     * 请求头
     */

    private org.bson.Document requestHeader;

    /**
     * 响应头
     */
    private org.bson.Document responseHeader;

    public InterfaceInfoDocument(NewInterfaceDTO newInterfaceDTO) {
        this.interfaceInfoId = newInterfaceDTO.getId();
        this.params = JSONUtil.parseObj(newInterfaceDTO.getParams()).toBean(org.bson.Document.class);
        this.requestHeader = JSONUtil.parseObj(newInterfaceDTO.getRequestHeader()).toBean(org.bson.Document.class);
        this.responseHeader = JSONUtil.parseObj(newInterfaceDTO.getResponseHeader()).toBean(org.bson.Document.class);
    }
}
