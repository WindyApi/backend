package top.whiteleaf03.api.modal.document.es;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author WhiteLeaf03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "windy_api")
public class InterfaceInfoESDocument {
    @Id
    @Field(type = FieldType.Keyword)
    private String _id;

    @Field(type = FieldType.Long, name = "interface_id")
    private Long interfaceId;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String describe;
}
