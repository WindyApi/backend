package top.whiteleaf03.api.modal.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.whiteleaf03.api.modal.entity.UserInterfaceRecord;

import java.util.Date;

/**
 * @author WhiteLeaf03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInterfaceRecordVO {
    /**
     * 接口id
     */
    private Long interfaceId;

    /**
     * 接口名称
     */
    private String interfaceName;

    /**
     * 总调用次数
     */
    private Integer totalNum;

    /**
     * 剩余调用次数
     */
    private Integer leftNum;

    /**
     * 更新时间
     */
    private Date updateTime;

    public UserInterfaceRecordVO(UserInterfaceRecord userInterfaceRecord, String interfaceName) {
        this.interfaceId = userInterfaceRecord.getInterfaceId();
        this.interfaceName = interfaceName;
        this.totalNum = userInterfaceRecord.getTotalNum();
        this.leftNum = userInterfaceRecord.getLeftNum();
        this.updateTime = userInterfaceRecord.getUpdateTime();
    }
}
