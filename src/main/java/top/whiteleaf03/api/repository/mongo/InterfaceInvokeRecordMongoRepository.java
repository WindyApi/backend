package top.whiteleaf03.api.repository.mongo;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import top.whiteleaf03.api.modal.dto.InterfaceInvokeRecordDTO;

@Repository
public interface InterfaceInvokeRecordMongoRepository extends MongoRepository<InterfaceInvokeRecordDTO, String> {
    @Aggregation(pipeline = {
            "{ $match: { currentTimestamp: { $lte: ?0, $gt: ?1 } } }",
            "{  $group: {_id: null, count: { $sum: 1 } } }"
    })
    Long calculateTotalSumInRange(long maxTime, long minTime);

    /**
     * 统计时间范围内 接口调用总耗时
     *
     * @param maxTime 最大时间
     * @param minTime 最小时间
     * @return 返回时间区间内，所有调用的总耗时
     */
    @Aggregation(pipeline = {
            "{ $match: { currentTimestamp: { $lte: ?0, $gt: ?1 } } }",
            "{ $group: { _id: null, totalUseTime: { $sum: \"$useTime\" } } }"
    })
    Long calculateTotalUseTimeInRange(long maxTime, long minTime);

    /**
     * 统计时间范围内，请求通过数量
     *
     * @param maxTime 最大时间
     * @param minTime 最小时间
     * @return 返回请求通过网关的次数
     */
    @Aggregation(pipeline = {
            "{ $match: { accept: { $e: true } } }",
            "{ $group: { _id: null, totalAccept: { $sum: \"accept\" } } }"
    })
    Long calculateTotalAcceptInRange(long maxTime, long minTime);
}
