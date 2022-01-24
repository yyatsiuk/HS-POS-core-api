package com.yyatsiuk.api.core.service.impl;

import com.yyatsiuk.api.core.enumerations.OrderStatus;
import com.yyatsiuk.api.core.enumerations.PaymentStatus;
import com.yyatsiuk.api.core.models.dto.OrderDto;
import com.yyatsiuk.api.core.models.entities.Courier;
import com.yyatsiuk.api.core.models.entities.Customer;
import com.yyatsiuk.api.core.models.entities.DeliveryInformation;
import com.yyatsiuk.api.core.models.entities.LineItem;
import com.yyatsiuk.api.core.models.entities.Order;
import com.yyatsiuk.api.core.models.entities.Product;
import com.yyatsiuk.api.core.models.request.LineItemRequest;
import com.yyatsiuk.api.core.models.request.OrderCreateRequest;
import com.yyatsiuk.api.core.repository.CourierRepository;
import com.yyatsiuk.api.core.repository.DeliveryInformationRepository;
import com.yyatsiuk.api.core.repository.OrderRepository;
import com.yyatsiuk.api.core.repository.ProductRepository;
import com.yyatsiuk.api.core.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final DeliveryInformationRepository deliveryInformationRepository;
    private final ProductRepository productRepository;
    private final CourierRepository courierRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            DeliveryInformationRepository deliveryInformationRepository,
                            ProductRepository productRepository,
                            CourierRepository courierRepository) {
        this.orderRepository = orderRepository;
        this.deliveryInformationRepository = deliveryInformationRepository;
        this.productRepository = productRepository;
        this.courierRepository = courierRepository;
    }

    @Transactional
    @Override
    public OrderDto save(OrderCreateRequest orderCreateRequest) {
        Courier courier = courierRepository.findByName(orderCreateRequest.getCourier()).orElseThrow(EntityNotFoundException::new);

        DeliveryInformation deliveryInformation = new DeliveryInformation();
        deliveryInformation.setAddress(orderCreateRequest.getAddress());
        deliveryInformation.setCourier(courier);
        deliveryInformation.setBranchNumber(orderCreateRequest.getBranchNumber());

        DeliveryInformation savedDeliveryInfo = deliveryInformationRepository.save(deliveryInformation);

        Order order = new Order();
        order.setCustomer(new Customer(orderCreateRequest.getCustomerId()));
        order.setDeliveryInformation(savedDeliveryInfo);
        order.setPrepaymentAmount(order.getPrepaymentAmount() == null ? BigDecimal.ZERO : order.getPrepaymentAmount());
        order.setPaymentStatus(PaymentStatus.UNPAID);
        order.setStatus(OrderStatus.PLACED);
        order.setNote(order.getNote());

        Map<Long, Product> products = getProducts(orderCreateRequest);
        List<LineItem> lineItems = toLineItems(orderCreateRequest, order, products);

        order.setItems(lineItems);

        orderRepository.save(order);

        return new OrderDto();
    }

    private Map<Long, Product> getProducts(OrderCreateRequest orderCreateRequest) {
        return productRepository.findAllByIdIn(orderCreateRequest.getItems()
                        .stream()
                        .map(LineItemRequest::getProductId)
                        .toList())
                .stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));
    }

    private List<LineItem> toLineItems(OrderCreateRequest orderCreateRequest, Order order, Map<Long, Product> products) {
        return orderCreateRequest
                .getItems()
                .stream()
                .map(item -> {
                    Product product = products.get(item.getProductId());
                    return new LineItem(order, product, item.getQuantity(), item.getDiscountAmount());
                }).collect(Collectors.toList());
    }

}
