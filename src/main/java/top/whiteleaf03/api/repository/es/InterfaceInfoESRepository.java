package top.whiteleaf03.api.repository.es;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import top.whiteleaf03.api.modal.document.es.InterfaceInfoESDocument;

import java.util.List;

@Repository
public interface InterfaceInfoESRepository extends ElasticsearchRepository<InterfaceInfoESDocument, String> {
    @Query("{\n" +
            "    \"multi_match\": {\n" +
            "      \"query\": \"?0\",\n" +
            "      \"fields\": [\"name\", \"describe\"]\n" +
            "    }\n" +
            "  }")
    List<InterfaceInfoESDocument> queryInterfaceInfoESDocumentByKeywordInNameOrDescribe(String keyword);
}
