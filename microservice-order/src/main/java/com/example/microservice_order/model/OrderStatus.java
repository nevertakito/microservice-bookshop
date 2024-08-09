package com.example.microservice_order.model;

/**
 * Pending — заказ создан, но не оплачен или не готов к выполнению.
 * Processing — заказ создан и оплачен, но не выполнен.
 * Canceled — заказ отменён и не должен быть выполнен.
 * Completed — все элементы заказа выполнены.
 * Error — этот статус заказа может быть вызван несколькими действиями и указывает на проблему с заказом.
 */

public enum OrderStatus {
    PENDING,
    PROCESSING,
    CANCELED,
    COMPLETED,
    ERROR;
}
