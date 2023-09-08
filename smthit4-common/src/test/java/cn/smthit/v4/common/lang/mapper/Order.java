package cn.smthit.v4.common.lang.mapper;

import lombok.Data;

/**
 * TODO
 *
 * @author bean
 * @date 2023/9/8
 */
@Data
public class Order {
    private Long id;
    private Address address;
    private Customer customer;
    private Long customerId;
    private Long customerId2;
    private Long addressId;
    //private String addressStreet2;
}
