package calebzhou.rdi.microservice.model;

import lombok.*;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;

/**
 * Created by calebzhou on 2022-09-09,7:37.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account {
    String id,pwd,regIp;
    Timestamp regTime;
}
