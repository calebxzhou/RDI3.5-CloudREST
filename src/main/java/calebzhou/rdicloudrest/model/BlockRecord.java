package calebzhou.rdicloudrest.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class BlockRecord {
    @Id
    private String recId;
    private String pid;
    private String blockId;
    private String act;
    private String world;
    private int posX;
    private int posY;
    private int posZ;
    private Timestamp recTime;
}
