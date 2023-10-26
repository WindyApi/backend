package top.whiteleaf03.api.modal.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NodeInfoVO {
    /**
     * 节点名称
     */
    private String name;

    /**
     * 节点IP
     */
    private String ip;

    /**
     * JVM内存用量
     */
    private Double memoryUsed;

    /**
     * CPU使用量
     */
    private Double cpuUsage;
}