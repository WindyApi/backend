package top.whiteleaf03.api.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class MongoUtil {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public MongoUtil(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public <T> void save(T t) {
        mongoTemplate.insert(t);
    }

    public List<?> find(String key, Object value, Class<?> clazz) {
        Query query = new Query(Criteria.where(key).is(value));
        return mongoTemplate.find(query, clazz);
    }

    public void update(String key, Object value, Class<?> clazz, Map<String, Object> data) {
        Update updateData = new Update();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            updateData.set(entry.getKey(), entry.getValue());
        }
        Query query = new Query(Criteria.where(key).is(value));
        mongoTemplate.updateFirst(query, updateData, clazz);
    }
}
