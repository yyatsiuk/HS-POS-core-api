package com.yyatsiuk.api.core.service.impl;

import com.yyatsiuk.api.core.enumerations.OrderStatus;
import com.yyatsiuk.api.core.enumerations.PaymentStatus;
import com.yyatsiuk.api.core.exceptions.EntityNotFoundException;
import com.yyatsiuk.api.core.models.dto.OrderDto;
import com.yyatsiuk.api.core.models.entities.Courier;
import com.yyatsiuk.api.core.models.entities.Customer;
import com.yyatsiuk.api.core.models.entities.DeliveryInformation;
import com.yyatsiuk.api.core.models.entities.LineItem;
import com.yyatsiuk.api.core.models.entities.Order;
import com.yyatsiuk.api.core.models.entities.Product;
import com.yyatsiuk.api.core.models.mappers.OrderMapper;
import com.yyatsiuk.api.core.models.request.LineItemRequest;
import com.yyatsiuk.api.core.models.request.OrderCreateRequest;
import com.yyatsiuk.api.core.repository.CourierRepository;
import com.yyatsiuk.api.core.repository.CustomerRepository;
import com.yyatsiuk.api.core.repository.DeliveryInformationRepository;
import com.yyatsiuk.api.core.repository.OrderRepository;
import com.yyatsiuk.api.core.repository.ProductRepository;
import com.yyatsiuk.api.core.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final CustomerRepository customerRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository,
                            DeliveryInformationRepository deliveryInformationRepository,
                            ProductRepository productRepository,
                            CourierRepository courierRepository,
                            CustomerRepository customerRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.deliveryInformationRepository = deliveryInformationRepository;
        this.productRepository = productRepository;
        this.courierRepository = courierRepository;
        this.customerRepository = customerRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional
    @Override
    public OrderDto save(OrderCreateRequest orderCreateRequest) {
        Customer customer = customerRepository.getById(orderCreateRequest.getCustomerId());
        Courier courier = courierRepository.findByName(orderCreateRequest.getCourier()).orElseThrow(EntityNotFoundException::new);

        DeliveryInformation deliveryInformation = new DeliveryInformation();
        deliveryInformation.setAddress(orderCreateRequest.getAddress());
        deliveryInformation.setCourier(courier);
        deliveryInformation.setBranchNumber(orderCreateRequest.getBranchNumber());

        DeliveryInformation savedDeliveryInfo = deliveryInformationRepository.save(deliveryInformation);

        Order order = new Order();
        order.setCustomer(customer);
        order.setDeliveryInformation(savedDeliveryInfo);
        order.setPrepaymentAmount(order.getPrepaymentAmount() == null ? BigDecimal.ZERO : order.getPrepaymentAmount());
        order.setPaymentStatus(PaymentStatus.UNPAID);
        order.setStatus(OrderStatus.PLACED);
        order.setNote(orderCreateRequest.getNotes());

        Map<Long, Product> products = getProducts(orderCreateRequest);
        List<LineItem> lineItems = toLineItems(orderCreateRequest, order, products);

        order.setItems(lineItems);

        orderRepository.save(order);

        return orderMapper.fromEntityToDto(order);
    }

    @Override
    public List<OrderDto> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::fromEntityToDto)
                .toList();
    }

    @Override
    public OrderDto findById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::fromEntityToDto)
                .orElseThrow(() -> new EntityNotFoundException("Product with id: {0} not found", id));
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
