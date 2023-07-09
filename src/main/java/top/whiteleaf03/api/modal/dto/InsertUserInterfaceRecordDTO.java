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
public class InsertUserInterfaceRecordDTO {
    /**
     * 调用用户 ID
     */
    private Long userId;

    /**
     * 接口 ID
     */
    private Long interfaceInfoId;

    /**
     * 总调用次数
     */
    private Integer totalNum;

    /**
     * 剩余调用次数
     */
    private Integer leftNum;

    public InsertUserInterfaceRecordDTO(UserSubscribeDTO userSubscribeDTO) {
        this.userId = userSubscribeDTO.getUserId();
        this.interfaceInfoId = userSubscribeDTO.getInterfaceInfoId();
        this.totalNum = userSubscribeDTO.getIncrease();
        this.leftNum = userSubscribeDTO.getIncrease();
    }
}
