package springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;

@Entity
@Getter
@Setter
@Table(name = "wikimedia_tbl")
public class Wikimedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Lob means (large object type)
    // The @Lob annotation is used to specify that the currently annotated entity attribute represents a large object type.
    @Lob
    private String wikiEvent;
}
