package cn.smthit.v4.common.lang.mapper;


import cn.smthit.v4.common.lang.kits.GsonKit;
import cn.smthit.v4.common.lang.kits.JacksonKit;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NameTokenizers;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author bean
 * @date 2023/9/8
 */
public class TestMapper {
    @Test
    public void testOne() {
        ModelMapper modelMapper = new ModelMapper();

        Order order = new Order();
        Customer customer = new Customer();

        Name name = new Name();
        name.setLastName("周");
        name.setFirstName("程宇");
        customer.setName(name);

        Address address = new Address();
        address.setCity("深圳");
        address.setStreet("龙岗区横岗街道");
        order.setCustomer(customer);
        order.setAddress(address);

        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        String jsonString = JacksonKit.toJson(orderDTO);
        System.out.println("jsonString = " + jsonString);
    }


    @Test
    public void testOne2() {
        ModelMapper modelMapper = new ModelMapper();

        Order order = new Order();

        order.setId(10L);
        order.setCustomerId(100L);
        order.setCustomerId2(1000000L);
        //order.setAddressStreet2("2");
        Customer customer = new Customer();
        //customer.setId(100L);

        Name name = new Name();
        name.setLastName("周");
        name.setFirstName("程宇");
        customer.setName(name);

        Address address = new Address();
        address.setId(1000L);

        address.setCity("深圳");
        address.setStreet("龙岗区横岗街道");
        address.setStreet2("龙岗区横岗街道2");

        order.setCustomer(customer);
        order.setAddress(address);

        OrderDTO dto = MapperKit.map(order, OrderDTO.class);
        String jsonString = JacksonKit.toJson(dto);

        System.out.println("jsonString = " + jsonString);
    }

    @Test
    public void testOne3() {
        ModelMapper modelMapper = new ModelMapper();

        Order order = new Order();

        order.setId(10L);

        //order.setAddressStreet2("2");
        Customer customer = new Customer();
        //customer.setId(100L);

        Name name = new Name();
        name.setLastName("周");
        name.setFirstName("程宇");
        customer.setName(name);

        Address address = new Address();
        address.setId(1000L);

        address.setCity("深圳");
        address.setStreet("龙岗区横岗街道");
        address.setStreet2("龙岗区横岗街道2");

        order.setCustomer(customer);
        order.setAddress(address);


        modelMapper.getConfiguration()
                .setAmbiguityIgnored(true)
                .setDeepCopyEnabled(true)
                .setImplicitMappingEnabled(true)
                .setCollectionsMergeEnabled(true)
                .setFieldMatchingEnabled(true)
                .setFullTypeMatchingRequired(true)
                .setPreferNestedProperties(false)
                .setSkipNullEnabled(true)
                .setFullTypeMatchingRequired(true)
                .setDestinationNameTokenizer(NameTokenizers.CAMEL_CASE)
                .setSourceNameTokenizer(NameTokenizers.CAMEL_CASE);

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        OrderDTO dto = modelMapper.map(order, OrderDTO.class);
        String jsonString = JacksonKit.toJson(dto);

        System.out.println("jsonString = " + jsonString);
    }

    @Test
    public void testList() {
        Order order = new Order();
        order.setId(10L);

        Customer customer = new Customer();
        customer.setId(100L);
        Name name = new Name();
        name.setLastName("周");
        name.setFirstName("程宇");
        customer.setName(name);
        Address address = new Address();
        address.setCity("深圳");
        address.setStreet("龙岗区横岗街道");
        order.setCustomer(customer);
        order.setAddress(address);

        Order order2 = new Order();
        Customer customer2 = new Customer();
        customer2.setId(101L);

        Name name2 = new Name();
        name2.setLastName("周");
        name2.setFirstName("晨曦");
        customer2.setName(name2);
        Address address2 = new Address();
        address2.setCity("深圳");
        address2.setStreet("盐田区梅沙街道");
        order2.setCustomer(customer2);
        order2.setAddress(address2);

        List<Order> containers =  new ArrayList<>();
        containers.add(order);
        containers.add(order2);

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.getConfiguration().setFullTypeMatchingRequired(true);
        List<OrderDTO> orderDTOs = modelMapper.map(containers, new TypeToken<List<OrderDTO>>() {}.getType());
        String jsonString = GsonKit.toJson(orderDTOs);
        System.out.println("jsonString = " + jsonString);
    }

    @Test
    public void testOne4() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        Order order = new Order();
        Customer customer = new Customer();
        Name name = new Name();
        name.setLastName("周");
        name.setFirstName("程宇");
        customer.setName(name);
        Address address = new Address();
        address.setCity("深圳");
        address.setStreet("盐田区梅沙街道");
        order.setCustomer(customer);
        order.setAddress(address);

        modelMapper.typeMap(Order.class, OrderDTO.class)
                .addMappings(mapper-> {
                    // 自定义属性转换
                    mapper.map(src -> src.getAddress().getStreet(), OrderDTO::setStreetTest);
                    mapper.map(src -> src.getAddress().getCity(), OrderDTO::setCityTest);
                });

        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);

        String jsonString = GsonKit.toJson(orderDTO);
        System.out.println("jsonString = " + jsonString);
    }
}
