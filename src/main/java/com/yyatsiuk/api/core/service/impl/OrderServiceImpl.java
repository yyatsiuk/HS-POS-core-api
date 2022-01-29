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

    private static final String ORDER_NOT_FOUND_MESSAGE = "Order with id: {0} not found";

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
        order.setPrepaymentAmount(orderCreateRequest.getPrepaymentAmount() == null ? BigDecimal.ZERO : orderCreateRequest.getPrepaymentAmount());
        order.setPaymentStatus(PaymentStatus.UNPAID);
        order.setStatus(OrderStatus.PLACED);
        order.setNote(orderCreateRequest.getNotes());

        List<LineItemRequest> items = orderCreateRequest.getItems();
        Map<Long, Product> products = getProducts(items);
        List<LineItem> lineItems = toLineItems(items, order, products);

        order.setItems(lineItems);

        orderRepository.save(order);

        return orderMapper.fromEntityToDto(order);
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderDto> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::fromEntityToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public OrderDto findById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::fromEntityToDto)
                .orElseThrow(() -> new EntityNotFoundException(ORDER_NOT_FOUND_MESSAGE, id));
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void updateStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(ORDER_NOT_FOUND_MESSAGE, id));

        order.setStatus(status);
        orderRepository.save(order);
    }

    @Override
    public void updatePaymentStatus(Long id, PaymentStatus paymentStatus) {
        Order order = orderRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(ORDER_NOT_FOUND_MESSAGE, id));

        order.setPaymentStatus(paymentStatus);
        orderRepository.save(order);
    }

    @Override
    public void updateOrderItems(Long id, List<LineItemRequest> items) {
        Order order = orderRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(ORDER_NOT_FOUND_MESSAGE, id));

        Map<Long, Product> products = getProducts(items);
        List<LineItem> lineItems = toLineItems(items, order, products);

        order.getItems().clear();
        order.getItems().addAll(lineItems);
        orderRepository.save(order);
    }

    private Map<Long, Product> getProducts(List<LineItemRequest> lineItems) {
        return productRepository.findAllByIdIn(lineItems
                        .stream()
                        .map(LineItemRequest::getProductId)
                        .toList())
                .stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));
    }

    private List<LineItem> toLineItems(List<LineItemRequest> lineItems, Order order, Map<Long, Product> products) {
        return lineItems
                .stream()
                .map(item -> {
                    Product product = products.get(item.getProductId());
                    return new LineItem(order, product, item.getQuantity(), item.getDiscountAmount());
                }).collect(Collectors.toList());
    }

}
