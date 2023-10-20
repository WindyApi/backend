package top.whiteleaf03.api.modal.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WhiteLeaf03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecentRecordVO {
    /**
     * 最近5分钟的QPS
     */
    Long[] recentQPS;

    /**
     * 最近5分钟请求平均耗时
     */
    Long[] recentAvgUseTime;

    /**
     * 最近5分钟调用成功率
     */
    Double[] recentAcceptRate;
}
