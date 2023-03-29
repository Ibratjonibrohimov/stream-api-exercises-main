package uz.najottalim.demo.streamapi;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import lombok.extern.slf4j.Slf4j;
import uz.najottalim.demo.streamapi.models.Customer;
import uz.najottalim.demo.streamapi.models.Order;
import uz.najottalim.demo.streamapi.models.Product;
import uz.najottalim.demo.streamapi.repos.CustomerRepo;
import uz.najottalim.demo.streamapi.repos.OrderRepo;
import uz.najottalim.demo.streamapi.repos.ProductRepo;

@Slf4j
@DataJpaTest
public class StreamApiTest {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductRepo productRepo;

    @Test
    @DisplayName("Produktlar listidan category = \"Books\" va price > 100 " +
            "bo'lgan produktlar listini oling")
    public void exercise1() {
        List<Product> expected = solution1();
        // hamma produktlarni olish
        List<Product> products = productRepo.findAll();
        System.out.println("----");
        products.forEach(
                product -> System.out.println(product)
        );
        System.out.println("----");
        // hamma orderlarni olish
        List<Order> orders = orderRepo.findAll();
        // hamma customerlarni olish
        List<Customer> customers = customerRepo.findAll();

        List<Product> ourSolution = products.stream()
                .filter(product -> product.getCategory().equalsIgnoreCase("Books"))
                .filter(product -> product.getPrice() > 100)
                .collect(Collectors.toList());
        Assertions.assertEquals(expected, ourSolution);
    }

//-----------------------------UYGA VAZIFA BOSHLANDI-------------------------

    //1 chi vazifa
    @Test
    @DisplayName("Produkt category = \"Baby\" bo'lgan orderlarni oling")
    public void exercise2() {
        List<Order> expected = solution2();
        // hamma orderlar
        List<Order> orders = orderRepo.findAll();
        //yordam 1: har bitta zakaz o'z ichiga oladigan Produktlar listini olish
        orders.forEach(order -> {
            Set<Product> products = order.getProducts();
        });
        //yordam 2: demak har bitta Orderni filter qilib, keyin har bitta
        // orderga tegishli produktlarni olib agar shu Produktni setni
        // ichidagi istalgan produktni kategorisi "Baby" bo'sa unda uni filterdan o'tqizab
        // qolganlarini tashlab yuborish kerak
        List<Order> yourSolution = orders.stream()
                .filter(order -> order.getProducts().stream()
                        .anyMatch(product -> product.getCategory().equalsIgnoreCase("Baby")))
                .collect(Collectors.toList());


//         pastdagi qator kommentdan ochilsin va method run qilinsin
//         yourSolution list yaratilgandan keyin
        Assertions.assertEquals(expected, yourSolution);
    }

    //2 chi vazifa
    @Test
    @DisplayName("category = “Toys” bo'lgan produktlarni oling va narxiga 10% diskountga o'zgartiring")
    public void exercise3() {
        List<Product> expected = solution3();

        List<Product> products = productRepo.findAll();
//         yordam: filter qilgandan song yangi produkt oching, chunki 10% diskount bilan
//        produkt qaytarilishi kerak
        List<Product> mySolution = products.stream()
                .filter(product -> product.getCategory().equalsIgnoreCase("Toys"))
                 .collect(Collectors.toList());
        mySolution.forEach(product -> product.setPrice(product.getPrice()*0.9));




//         pastdagi qator kommentdan ochilsin va method run qilinsin
//         yourSolution list yaratilgandan keyin
        Assertions.assertEquals(expected, mySolution);
    }


    //3 chi vazifa
    @Test
    @DisplayName("01-Feb-2021 va 01-Apr-2021 oralig'ida va Customerni tier 2 bo'lgan" +
            "Orderlarni chiqaring")
    public void exercise4_1() {
        List<Order> expected = solution4_1();
        // yordam:
        // shu oraliqdagi zakazlarni olib
        // zakaz qilgan customerni tier boyicha filter qilib
        // chiqarish kerak
    }

    //4 chi vazifa
    @Test
    @DisplayName("Statusi NEW bo'lgan orderlarni zakaz qilgan Customerlarni nomini chiqaring" +
            "Orderlarni chiqaring")
    public void exercise4_2() {
        List<String> expected = solution4_2();
        // yordam:
        // birinchi filter keyn map order.getCustomer qilib
        // customerlarni olsa boladi
    }

    //5 chi vazifa
    @Test
    @DisplayName("Calculate the average price of all orders placed on 15-Mar-2021" +
            "15-Mar-2021 zakaz qilingan produktlarni o'rtacha narxini hisoblang")
    public void exercise9() {
        double expected = solution9();
        // yordam: birinchi shu sanadagi hamma orderni olib
        // keyn har bir orderni produktlarini listga yiging
        // keyn narximi stream average bilan hisoblang
    }

    //6 chi vazifa
    @Test
    @DisplayName("Calculate the total lump of all orders placed in Feb 2021" +
            "Feb 2021 zakaz qilingan produktlar narxini hisoblang")
    public void exercise8() {
        double expected = solution8();
        // yordam: birinchi shu sanadagi hamma orderni olib
        // keyn har bir orderni produktlarini listga yiging
        // keyn narximi stream sum bilan hisoblang
    }

    //7 chi vazifa
    @Test
    @DisplayName("Obtain statistics summary of all products belong to \"Books\" category" +
            "\"Books\" category bo'lgan produktlarni summary statistikasini oling")
    public void exercise10() {
        DoubleSummaryStatistics expected = solution10();
        // yordam: produktni kategoriya boyicha filter qiling
        // keyn streamdan DoubleStreamga o'ting va
        // summary statisticsni chiqaring
    }

    //8 chi vazifa
    @Test
    @DisplayName("Get a list of products which was ordered on 15-Mar-2021" +
            "15-Mar-2021 zakaz qilingan produktlarni oling")
    public void exercise7() {
        List<Product> expected = solution7();
        // yordam (murakkamroq): birinchi shu sanadagi orderlarni olin
        // keyn har bir orderga tegishli produktni
        // olish kerak
    }

    //9 chi vazifa

    @Test
    @DisplayName("xamma 2021 zakaz qilingan" +
            "zakazlarni eng kop zakaz qilgan 10 ta customerni chiqaring")
    public void exercise4_3() {
        List<Customer> expected = solution4_3();
        // yordam (murakkamroq):
        // birinchi xamma 2021 zakazlarni olish kerak
        // keyin map ochib xar bitta zakaz uchun produktlar
        // sonini sanash kerak keyn ularni count boyicha saralb
        // 10 ta eng counti kattasini chiqarish kerak
    }


    //-----------------------------UYGA VAZIFA TUGADI-------------------------


    // Collectors joining (fizz, buzz)
    @Test
    @DisplayName("Obtain a list of products ordered by customer of tier 2 between 01-Feb-2021 and 01-Apr-2021" +
            "01-Feb-2021 va 01-Apr-2021 oralig'ida zakaz qilingan produktlarni oling")
    public void exercise4() {
        List<Product> expected = solution4();

    }

    @Test
    @DisplayName("Tepadagi topshiriq bilan bir xil faqat boshqa usulda yechim berilgan")
    public void exercise1a() {
        List<Product> expected = solution1a();


    }

    @Test
    @DisplayName("Obtain a list of product with category = \"Books\" and price > 100 (using BiPredicate for filter)")
    public void exercise1b() {
        List<Product> expected = solution1b();
    }


    @Test
    @DisplayName("Eng arzon category \"Books\" bo'lgan produktni oling")
    public void exercise5() {
        Optional<Product> expected = solution5();
        Optional<Product> result = productRepo.findAll()
                .stream()
                .filter(p -> p.getCategory().equalsIgnoreCase("Books"))
                .sorted(Comparator.comparing(Product::getPrice))
                .findFirst();
        Assertions.assertEquals(expected, result);
        log.error("our solution: {}", result);
    }


    @Test
    @DisplayName("Get the 3 most recent placed order" +
            "Eng oxiri zakaz qilingan 3 zakazni oling")
    public void exercise6() {
        List<Order> expected = solution6();
    }


//    @Test
//    @DisplayName("Calculate the total lump of all orders placed in Feb 2021 (using reduce with BiFunction)")
//    public void exercise8a() {
//        double expected = solution8a();
//        List<Order> orders = orderRepo.findAll();
//        double ourSolution = orders.stream()
//                .filter(order -> order.getOrderDate()
//                        .isBefore(LocalDate
//                                .of(2021,
//                                        Month.MARCH,
//                                        1)
//                        ))
//                .filter(order -> order.getOrderDate().isAfter(LocalDate.of(2021, Month.JANUARY, 31)))
//                .flatMap(order -> order.getProducts().stream().map(Product::getPrice))
//                .reduce(0D, Double::sum, Double::sum);
//        Assertions.assertEquals(expected, ourSolution);
//        System.out.println(expected);
//        System.out.println(ourSolution);
////        List<Product> products = productRepo.findAll();
////        Stream<Product> productStream = products.stream();
////        DoubleSummaryStatistics summaryStatistics = productStream
////                .filter(product -> product.getCategory().equalsIgnoreCase("Games"))
////                .mapToDouble(Product::getPrice)
////                .summaryStatistics();
////
////
////        System.out.println(summaryStatistics);
//    }


    @Test
    @DisplayName("Obtain a mapping of order id and the order's product count" +
            "order idni order produkt kountiga mapini oling")
    public void exercise11() {
        Map<Long, Integer> expected = solution11();
    }


    @Test
    @DisplayName("Obtain a data map of customer and list of orders" +
            "Customerni orderlarini mapini oling")
    public void exercise12() {
        Map<Customer, List<Order>> expected = solution12();
    }


    @Test
    @DisplayName("Obtain a data map of customer_id and list of order_id(s)" +
            "Customer idlarini order idlarga mapini oling")
    public void exercise12a() {
        HashMap<Long, List<Long>> expected = solution12a();
    }


    @Test
    @DisplayName("Obtain a data map with order and its total price" +
            "Orderlarni umumiy narxga mapini oling")
    public void exercise13() {
        Map<Order, Double> expected = solution13();
    }


    @Test
    @DisplayName("Obtain a data map with order and its total price (using reduce)" +
            "Orderlarni umumiy narxga mapini oling (reduce bilan)")
    public void exercise13a() {
        Map<Long, Double> expected = solution13a();
    }

    @Test
    @DisplayName("Obtain a data map of product name by category" +
            "Produkt nomini categoriyaga boglab chiqaring")
    public void exercise14() {
        Map<String, List<String>> expected = solution14();
    }


    @Test
    @DisplayName("Get the most expensive product per category" +
            "har bitta Categoriyaga to'g'ri keladigan eng qimmat Productni oling")
    void exercise15() {
        Map<String, Optional<Product>> expected = solution15();
//		result.forEach((k,v) -> {
//			log.info("key=" + k + ", value=" + v.get());
//		});

    }


    @Test
    @DisplayName("Get the most expensive product (by name) per category" +
            "har bitta Categoriyaga to'g'ri keladigan eng qimmat Productni (nomini) oling")
    void exercise15a() {
        Map<String, String> expected = solution15a();
    }

    private List<Product> solution1() {
        long startTime = System.currentTimeMillis();
        List<Product> result = productRepo.findAll()
                .stream()
                .filter(p -> p.getCategory().equalsIgnoreCase("Books"))
                .filter(p -> p.getPrice() > 100)
                .collect(Collectors.toList());
        long endTime = System.currentTimeMillis();

        log.info(String.format("exercise 1 - execution time: %1$d ms", (endTime - startTime)));
        result.forEach(p -> log.info(p.toString()));
        return result;
    }

    private List<Product> solution1a() {
        Predicate<Product> categoryFilter = product -> product.getCategory().equalsIgnoreCase("Books");
        Predicate<Product> priceFilter = product -> product.getPrice() > 100;

        long startTime = System.currentTimeMillis();
        List<Product> result = productRepo.findAll()
                .stream()
                .filter(product -> categoryFilter.and(priceFilter).test(product))
                .collect(Collectors.toList());
        long endTime = System.currentTimeMillis();

        log.info(String.format("exercise 1a - execution time: %1$d ms", (endTime - startTime)));
        result.forEach(p -> log.info(p.toString()));
        return result;
    }

    private List<Product> solution1b() {
        BiPredicate<Product, String> categoryFilter = (product, category) -> product.getCategory().equalsIgnoreCase(category);

        long startTime = System.currentTimeMillis();
        List<Product> result = productRepo.findAll()
                .stream()
                .filter(product -> categoryFilter.test(product, "Books") && product.getPrice() > 100)
                .collect(Collectors.toList());
        long endTime = System.currentTimeMillis();

        log.info(String.format("exercise 1b - execution time: %1$d ms", (endTime - startTime)));
        result.forEach(p -> log.info(p.toString()));
        return result;
    }

    private List<Order> solution2() {
        long startTime = System.currentTimeMillis();
        List<Order> result = orderRepo.findAll()
                .stream()
                .filter(o ->
                        o.getProducts()
                                .stream()
                                .anyMatch(p -> p.getCategory().equalsIgnoreCase("Baby"))
                )
                .collect(Collectors.toList());

        long endTime = System.currentTimeMillis();

        log.info(String.format("exercise 2 - execution time: %1$d ms", (endTime - startTime)));
        result.forEach(o -> log.info(o.toString()));
        return result;
    }

    private List<Product> solution3() {
        long startTime = System.currentTimeMillis();

        List<Product> result = productRepo.findAll()
                .stream()
                .filter(p -> p.getCategory().equalsIgnoreCase("Toys"))
                .map(p -> p.withPrice(p.getPrice() * 0.9))
                .collect(Collectors.toList());

        long endTime = System.currentTimeMillis();
        log.info(String.format("exercise 3 - execution time: %1$d ms", (endTime - startTime)));
        result.forEach(o -> log.info(o.toString()));
        return result;
    }

    private List<Order> solution4_1() {
        List<Order> result = orderRepo.findAll()
                .stream()
                .filter(o -> o.getCustomer().getTier() == 2)
                .filter(o -> !o.getOrderDate().isBefore(LocalDate.of(2021, 2, 1)))
                .filter(o -> !o.getOrderDate().isAfter(LocalDate.of(2021, 4, 1)))
                .collect(Collectors.toList());
        return result;
    }

    private List<String> solution4_2() {

        List<String> result = orderRepo.findAll()
                .stream()
                .filter(o -> o.getStatus().equalsIgnoreCase("NEW"))
                .map(o -> o.getCustomer())
                .map(o -> o.getName())
                .collect(Collectors.toList());
        return result;
    }

    private List<Customer> solution4_3() {
//        "xamma 2021 zakaz qilingan" +
//                "zakazlarni eng kop zakaz qilgan 10 ta customerni chiqaring"
        List<Customer> topCustomers =
                orderRepo.findAll()
                        .stream()
                        .filter(o -> o.getOrderDate().getYear() == 2021)
                        .map(Order::getCustomer)
                        .collect(Collectors.groupingBy(
                                (customer -> customer),
                                Collectors.counting()
                        ))
                        .entrySet()
                        .stream()
                        .sorted((cc1, cc2) -> Long.compare(cc2.getValue(), cc1.getValue()))
                        .peek(entry -> System.out.println(entry))
                        .map(entry -> entry.getKey())
                        .collect(Collectors.toList());

        return topCustomers;
    }

    private List<Product> solution4() {
        long startTime = System.currentTimeMillis();
        List<Product> result = orderRepo.findAll()
                .stream()
                .filter(o -> o.getCustomer().getTier() == 2)
                .filter(o -> !o.getOrderDate().isBefore(LocalDate.of(2021, 2, 1)))
                .filter(o -> !o.getOrderDate().isAfter(LocalDate.of(2021, 4, 1)))
                .flatMap(o -> o.getProducts().stream())
                .distinct()
                .collect(Collectors.toList());

        long endTime = System.currentTimeMillis();
        log.info(String.format("exercise 4 - execution time: %1$d ms", (endTime - startTime)));
        result.forEach(o -> log.info(o.toString()));
        return result;
    }

    private Optional<Product> solution5() {
        long startTime = System.currentTimeMillis();
//              Optional<Product> result = productRepo.findAll()
//                              .stream()
//                              .filter(p -> p.getCategory().equalsIgnoreCase("Books"))
//                              .sorted(Comparator.comparing(Product::getPrice))
//                              .findFirst();

        Optional<Product> result = productRepo.findAll()
                .stream()
                .filter(p -> p.getCategory().equalsIgnoreCase("Books"))
                .min(Comparator.comparing(Product::getPrice));

        long endTime = System.currentTimeMillis();
        log.info(String.format("exercise 5 - execution time: %1$d ms", (endTime - startTime)));
        log.info(result.get().toString());
        return result;
    }

    private List<Order> solution6() {
        long startTime = System.currentTimeMillis();
        List<Order> result = orderRepo.findAll()
                .stream()
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .limit(3)
                .collect(Collectors.toList());

        long endTime = System.currentTimeMillis();
        log.info(String.format("exercise 6 - execution time: %1$d ms", (endTime - startTime)));
        result.forEach(o -> log.info(o.toString()));
        return result;
    }

    private List<Product> solution7() {
        long startTime = System.currentTimeMillis();
        List<Product> result = orderRepo.findAll()
                .stream()
                .filter(o -> o.getOrderDate().isEqual(LocalDate.of(2021, 3, 15)))
                .peek(o -> System.out.println(o.toString()))
                .flatMap(o -> o.getProducts().stream())
                .distinct()
                .collect(Collectors.toList());

        long endTime = System.currentTimeMillis();
        log.info(String.format("exercise 7 - execution time: %1$d ms", (endTime - startTime)));
        result.forEach(o -> log.info(o.toString()));
        return result;
    }

    private double solution8() {
        long startTime = System.currentTimeMillis();
        double result = orderRepo.findAll()
                .stream()
                .filter(o -> !o.getOrderDate().isBefore(LocalDate.of(2021, 2, 1)))
                .filter(o -> o.getOrderDate().isBefore(LocalDate.of(2021, 3, 1)))
                .flatMap(o -> o.getProducts().stream())
                .mapToDouble(Product::getPrice)
                .sum();

        long endTime = System.currentTimeMillis();
        log.info(String.format("exercise 8 - execution time: %1$d ms", (endTime - startTime)));
        log.info("Total lump sum = " + result);
        return result;
    }

    private double solution8a() {
        BiFunction<Double, Product, Double> accumulator = (acc, product) -> acc + product.getPrice();

        long startTime = System.currentTimeMillis();
        double result = orderRepo.findAll()
                .stream()
                .filter(o -> !o.getOrderDate().isBefore(LocalDate.of(2021, 2, 1)))
                .filter(o -> o.getOrderDate().isBefore(LocalDate.of(2021, 3, 1)))
                .flatMap(o -> o.getProducts().stream())
                .reduce(0D, accumulator, Double::sum);

        long endTime = System.currentTimeMillis();
        log.info(String.format("exercise 8a - execution time: %1$d ms", (endTime - startTime)));
        log.info("Total lump sum = " + result);
        return result;
    }

    private double solution9() {
        long startTime = System.currentTimeMillis();
        double result = orderRepo.findAll()
                .stream()
                .filter(o -> o.getOrderDate().isEqual(LocalDate.of(2021, 3, 15)))
                .flatMap(o -> o.getProducts().stream())
                .mapToDouble(Product::getPrice)
                .average().getAsDouble();

        long endTime = System.currentTimeMillis();
        log.info(String.format("exercise 9 - execution time: %1$d ms", (endTime - startTime)));
        log.info("Average = " + result);
        return result;
    }

    private DoubleSummaryStatistics solution10() {
        long startTime = System.currentTimeMillis();
        DoubleSummaryStatistics statistics = productRepo.findAll()
                .stream()
                .filter(p -> p.getCategory().equalsIgnoreCase("Books"))
                .mapToDouble(Product::getPrice)
                .summaryStatistics();

        long endTime = System.currentTimeMillis();
        log.info(String.format("exercise 10 - execution time: %1$d ms", (endTime - startTime)));
        log.info(String.format("count = %1$d, average = %2$f, max = %3$f, min = %4$f, sum = %5$f",
                statistics.getCount(), statistics.getAverage(), statistics.getMax(), statistics.getMin(), statistics.getSum()));
        return statistics;
    }

    private Map<Long, Integer> solution11() {
        long startTime = System.currentTimeMillis();
        Map<Long, Integer> result = orderRepo.findAll()
                .stream()
                .collect(
                        Collectors.toMap(
                                Order::getId,
                                order -> order.getProducts().size())
                );

        long endTime = System.currentTimeMillis();
        log.info(String.format("exercise 11 - execution time: %1$d ms", (endTime - startTime)));
        log.info(result.toString());
        return result;
    }

    private Map<Customer, List<Order>> solution12() {
        long startTime = System.currentTimeMillis();
        Map<Customer, List<Order>> result = orderRepo.findAll()
                .stream()
                .collect(Collectors.groupingBy(Order::getCustomer));

        long endTime = System.currentTimeMillis();
        log.info(String.format("exercise 12 - execution time: %1$d ms", (endTime - startTime)));
        log.info(result.toString());
        return result;
    }

    private HashMap<Long, List<Long>> solution12a() {
        long startTime = System.currentTimeMillis();
        HashMap<Long, List<Long>> result = orderRepo.findAll()
                .stream()
                .collect(
                        Collectors.groupingBy(
                                order -> order.getCustomer().getId(),
                                HashMap::new,
                                Collectors.mapping(Order::getId, Collectors.toList())));
        long endTime = System.currentTimeMillis();
        log.info(String.format("exercise 12a - execution time: %1$d ms", (endTime - startTime)));
        log.info(result.toString());
        return result;
    }

    private Map<Order, Double> solution13() {
        long startTime = System.currentTimeMillis();
        Map<Order, Double> result = orderRepo.findAll()
                .stream()
                .collect(
                        Collectors.toMap(
                                Function.identity(),
                                order -> order.getProducts().stream()
                                        .mapToDouble(Product::getPrice).sum())
                );

        long endTime = System.currentTimeMillis();
        log.info(String.format("exercise 13 - execution time: %1$d ms", (endTime - startTime)));
        log.info(result.toString());
        return result;
    }

    private Map<Long, Double> solution13a() {
        long startTime = System.currentTimeMillis();
        Map<Long, Double> result = orderRepo.findAll()
                .stream()
                .collect(
                        Collectors.toMap(
                                Order::getId,
                                order -> order.getProducts().stream()
                                        .reduce(0D, (acc, product) -> acc + product.getPrice(), Double::sum)
                        ));

        long endTime = System.currentTimeMillis();
        log.info(String.format("exercise 13a - execution time: %1$d ms", (endTime - startTime)));
        log.info(result.toString());
        return result;
    }

    private Map<String, List<String>> solution14() {
        long startTime = System.currentTimeMillis();
        Map<String, List<String>> result = productRepo.findAll()
                .stream()
                .collect(
                        Collectors.groupingBy(
                                Product::getCategory,
                                Collectors.mapping(Product::getName, Collectors.toList()))
                );


        long endTime = System.currentTimeMillis();
        log.info(String.format("exercise 14 - execution time: %1$d ms", (endTime - startTime)));
        log.info(result.toString());
        return result;
    }

    private Map<String, Optional<Product>> solution15() {
        long startTime = System.currentTimeMillis();
        Map<String, Optional<Product>> result = productRepo.findAll()
                .stream()
                .collect(
                        Collectors.groupingBy(
                                Product::getCategory,
                                Collectors.maxBy(Comparator.comparing(Product::getPrice)))
                );
        long endTime = System.currentTimeMillis();
        log.info(String.format("exercise 15 - execution time: %1$d ms", (endTime - startTime)));
        log.info(result.toString());
        return result;
    }

    private Map<String, String> solution15a() {
        long startTime = System.currentTimeMillis();
        Map<String, String> result = productRepo.findAll()
                .stream()
                .collect(
                        Collectors.groupingBy(
                                Product::getCategory,
                                Collectors.collectingAndThen(
                                        Collectors.maxBy(Comparator.comparingDouble(Product::getPrice)),
                                        optionalProduct -> optionalProduct.map(Product::getName).orElse(null)
                                )
                        ));
        long endTime = System.currentTimeMillis();
        log.info(String.format("exercise 15a - execution time: %1$d ms", (endTime - startTime)));
        log.info(result.toString());
        return result;
    }
}
