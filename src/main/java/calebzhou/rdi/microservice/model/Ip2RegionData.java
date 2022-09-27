package calebzhou.rdi.microservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by calebzhou on 2022-09-27,22:17.
 */
@Getter
@ToString
@AllArgsConstructor
public class Ip2RegionData {
    String nation,province,city,isp;
}
