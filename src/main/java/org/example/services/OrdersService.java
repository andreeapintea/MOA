package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.example.controllers.ClientmainController;
import org.example.exceptions.CouldNotWriteOrdersException;
import org.example.exceptions.ProductAlreadyExists;
import org.example.model.Order;
import org.example.model.Product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.*;

public class OrdersService {

        private static List<Order> orders;
        private static final Path ORDERS_PATH = FileSystemService.getPathToFile(new String[]{"config", "orders.json"});

        public OrdersService() {
        }

        public static void loadOrdersFromFile() throws IOException {
            if (!Files.exists(ORDERS_PATH, new LinkOption[0])) {
                FileUtils.copyURLToFile(OrdersService.class.getClassLoader().getResource("orders.json"), ORDERS_PATH.toFile());
            }

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            orders = (List)objectMapper.readValue(ORDERS_PATH.toFile(), new TypeReference<List<Order>>() {
            });
        }

        public static void addOrder( String clientUsername, Product product)  {

            Random rand = new Random();
            int orderNo = rand.nextInt(1000);
            //int orderNo = (int )(Math.random() * 100 + 1);

            orders.add( new Order(clientUsername, product, orderNo, "pending"));
            persistOrders();
        }


        private static void persistOrders() {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(ORDERS_PATH.toFile(), orders);
            } catch (IOException var1) {
                throw new CouldNotWriteOrdersException();
            }
        }
        public static List<Order> getClientOrders(String Username)throws Exception {
            OrdersService.loadOrdersFromFile();
            List<Order> order = new ArrayList<>();
            for (Order or : orders) {
                if (Objects.equals(Username, or.getClientUsername())) {
                    order.add(or);
                }
            }
            return order;
        }

    public static List<Order> getBrandOrders(String Username)throws Exception {
        OrdersService.loadOrdersFromFile();
        List<Order> order = new ArrayList<>();
        for (Order or : orders) {
            if (Objects.equals(Username, or.getProduct().getBrandUsername())) {
                order.add(or);
            }
        }
        return order;
    }


}
