package com.geekbrains.geekmarketwinter.controllers;

import com.geekbrains.geekmarketwinter.entities.OrderItem;
import com.geekbrains.geekmarketwinter.entities.User;
import com.geekbrains.geekmarketwinter.services.ProductService;
import com.geekbrains.geekmarketwinter.utils.Order;
import com.geekbrains.geekmarketwinter.utils.ShoppingCart;
import contract.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import contract.specifications.ProductSpecs;
import com.geekbrains.geekmarketwinter.services.SendMessageService;
import com.geekbrains.geekmarketwinter.services.ShoppingCartService;
import com.geekbrains.geekmarketwinter.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public String placeOrder(Model model, HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        String userName = httpServletRequest.getUserPrincipal().getName();
        double totalCost = shoppingCartService.getTotalCost(httpSession);
        String msg = String.format("New order created. User = %s, total cost = %.2f", userName, totalCost);

        ShoppingCart cart = shoppingCartService.getCurrentCart(httpSession);
        User user = (User) httpSession.getAttribute("user");
        List<OrderItem> orderItems = cart.getItems();
        double price = cart.getTotalCost();

        Order order = new Order(user, orderItems, price);
        model.addAttribute("order", order);

        sendMessageService.sendMessage(QUEUE_NAME, msg);

        return "order-result";
    }
}
