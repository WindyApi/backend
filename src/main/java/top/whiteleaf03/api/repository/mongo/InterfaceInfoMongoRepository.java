package top.whiteleaf03.api.repository.mongo;

import org.bson.Document;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import top.whiteleaf03.api.modal.document.InterfaceInfoDocument;

import java.util.*;

public interface InterfaceInfoMongoRepository extends MongoRepository<InterfaceInfoDocument, String> {
    @Aggregation(pipeline = {
            "{ $match: { interfaceInfoId: ?0 } }",
            "{ $set: { requestHeader: ?1, responseHeader: ?2, params:?3 }}",
            "{ $out: 'interface_info'}"
    })
    void update(Long id, Document requestHeader, Document responseHeader, Document params);

    List<InterfaceInfoDocument> findByInterfaceInfoId(Long interfaceInfoId);
}
