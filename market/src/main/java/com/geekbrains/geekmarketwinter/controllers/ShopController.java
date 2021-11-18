package com.geekbrains.geekmarketwinter.controllers;

import com.geekbrains.geekmarketwinter.entities.DeliveryAddress;
import com.geekbrains.geekmarketwinter.entities.Order;
import com.geekbrains.geekmarketwinter.entities.OrderItem;
import com.geekbrains.geekmarketwinter.entities.User;
import com.geekbrains.geekmarketwinter.services.*;
import com.geekbrains.geekmarketwinter.utils.ShoppingCart;
import contract.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import contract.specifications.ProductSpecs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/shop")
public class ShopController {
    private static final int INITIAL_PAGE = 0;
    private static final int PAGE_SIZE = 5;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private ShoppingCartService shoppingCartService;
    
    @Autowired
    private SendMessageService sendMessageService;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private DeliveryAddressService deliveryAddressService;

    @GetMapping
    public String shopPage(Model model,
                           @RequestParam(value = "page") Optional<Integer> page,
                           @RequestParam(value = "word", required = false) String word,
                           @RequestParam(value = "min", required = false) Double min,
                           @RequestParam(value = "max", required = false) Double max
    ) {
        final int currentPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Specification<Product> spec = Specification.where(null);
        StringBuilder filters = new StringBuilder();
        if (word != null) {
            spec = spec.and(ProductSpecs.titleContains(word));
            filters.append("&word=" + word);
        }
        if (min != null) {
            spec = spec.and(ProductSpecs.priceGreaterThanOrEq(min));
            filters.append("&min=" + min);
        }
        if (max != null) {
            spec = spec.and(ProductSpecs.priceLesserThanOrEq(max));
            filters.append("&max=" + max);
        }

        Page<Product> products = productService.getProductsWithPagingAndFiltering(currentPage, PAGE_SIZE, word, min, max);

        model.addAttribute("products", products.getContent());
        model.addAttribute("page", currentPage);
        model.addAttribute("totalPage", products.getTotalPages());

        model.addAttribute("filters", filters.toString());

        model.addAttribute("min", min);
        model.addAttribute("max", max);
        model.addAttribute("word", word);
        
        return "shop-page";
    }

    @GetMapping("/cart/add/{id}")
    public String addProductToCart(Model model, @PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        shoppingCartService.addToCart(httpServletRequest.getSession(), id);
        String referrer = httpServletRequest.getHeader("referer");

        return "redirect:" + referrer;
    }

    private final static String QUEUE_NAME = "orderQueue";
    
    @GetMapping("/order/fill")
    public String orderFill(Model model, HttpServletRequest httpServletRequest, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        User user = userService.findByUserName(principal.getName());
        Order order = orderService.makeOrder(shoppingCartService.getCurrentCart(httpServletRequest.getSession()), user);
        List<DeliveryAddress> deliveryAddresses = deliveryAddressService.getUserAddresses(user.getId());
        model.addAttribute("order", order);
        model.addAttribute("deliveryAddresses", deliveryAddresses);
        return "order-filler";
    }
    
//    @GetMapping("/order/confirm")
//    public String placeOrder(Model model, HttpServletRequest httpServletRequest) {
//        HttpSession httpSession = httpServletRequest.getSession();
//        String userName = httpServletRequest.getUserPrincipal().getName();
//        double totalCost = shoppingCartService.getTotalCost(httpSession);
//        String msg = String.format("New order created. User = %s, total cost = %.2f", userName, totalCost);
//
//        ShoppingCart cart = shoppingCartService.getCurrentCart(httpSession);
//        User user = (User) httpSession.getAttribute("user");
//        List<OrderItem> orderItems = cart.getItems();
//        double price = cart.getTotalCost();
//
//        Order order = orderService.makeOrder(shoppingCartService.getCurrentCart(httpServletRequest.getSession()), user);
//        model.addAttribute("order", order);
//
//        sendMessageService.sendMessage(QUEUE_NAME, msg);
//
//        return "order-result";
//    }
    
    @PostMapping("/order/confirm")
    public String orderConfirm(Model model, HttpServletRequest httpServletRequest, @ModelAttribute(name = "order") Order orderFromFrontend, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        User user = userService.findByUserName(principal.getName());
        Order order = orderService.makeOrder(shoppingCartService.getCurrentCart(httpServletRequest.getSession()), user);
        order.setDeliveryAddress(orderFromFrontend.getDeliveryAddress());
        order.setPhoneNumber(orderFromFrontend.getPhoneNumber());
        order.setDeliveryDate(LocalDateTime.now().plusDays(7));
        order.setDeliveryPrice(0.0);
        order = orderService.saveOrder(order);
        model.addAttribute("order", order);
        return "order-filler";
    }
    
    @GetMapping("/order/result/{id}")
    public String orderConfirm(Model model, @PathVariable(name = "id") Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        // todo ждем до оплаты, проверка безопасности и проблема с повторной отправкой письма сделать одноразовый вход
        User user = userService.findByUserName(principal.getName());
        Order confirmedOrder = orderService.findById(id);
        if (!user.getId().equals(confirmedOrder.getUser().getId())) {
            return "redirect:/";
        }
//        mailService.sendOrderMail(confirmedOrder);
        model.addAttribute("order", confirmedOrder);
        return "order-result";
    }
    
    
}
