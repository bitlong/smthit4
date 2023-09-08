package cn.smthit.v4.common.lang.mapper;

import lombok.Data;

/**
 * TODO
 *
 * @author bean
 * @date 2023/9/8
 */
@Data
public class OrderDTO {
    private Long id;

    private Long   customerId;
    private String customerFirstName;
    private String customerLastName;
    private String addressStreet;
    private String addressCity;
    private Long   addressId;
    private String streetTest;
    private String cityTest;
    private String addressStreet2;
}
