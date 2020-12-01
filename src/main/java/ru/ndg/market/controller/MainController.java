package ru.ndg.market.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ndg.market.bean.Cart;
import ru.ndg.market.model.Order;
import ru.ndg.market.model.Product;
import ru.ndg.market.model.User;
import ru.ndg.market.service.order.OrderService;
import ru.ndg.market.service.product.ProductService;
import ru.ndg.market.service.user.UserService;

import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping(value = "/")
public class MainController {

    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;
    private final Cart cart;

    @Autowired
    public MainController(ProductService productService, UserService userService, OrderService orderService, Cart cart) {
        this.productService = productService;
        this.userService = userService;
        this.orderService = orderService;
        this.cart = cart;
    }

    @GetMapping
    public String showProductsPage(@RequestParam(name = "p", required = false) Integer page
            , @RequestParam(required = false) Map<String, String> params
            , Model model) {
        model.addAttribute("productsPage", productService.getAllProducts(params, page));
        model.addAttribute("filtersDef", params.get("filterOut"));
        return "index";
    }

    @GetMapping(value = "/login")
    public String showLoginPage() {
        return "login_page";
    }

    @GetMapping(value = "/sign_up")
    public String showRegistrationPage() {
        return "sign_up";
    }

    @PostMapping(value = "/user/create")
    public String createNewUser(@ModelAttribute User user) {
        userService.saveNewUser(user);
        return "redirect:/login";
    }

    @GetMapping(value = "/orders")
    public String showOrdersPage(@RequestParam(name = "p", required = false) Integer page
            , @RequestParam(required = false) Map<String, String> params
            , Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("ordersPage", orderService.getAllOrdersByUser(user, page));
        // TODO: 01.12.2020 Доработать фильтры на заказы
//        model.addAttribute("filtersDef", params.get("filterOut"));
        return "orders";
    }

    @GetMapping(value = "/edit/{id}")
    public String showEditProductPage(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "edit_product";
    }

    @PostMapping(value = "/edit")
    public String editProduct(@ModelAttribute Product product) {
        productService.saveOrUpdateProduct(product);
        return "redirect:/";
    }

    @GetMapping(value = "/profile")
    public String showProfilePage(Model model, Principal principal) {
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        return "profile";
    }

    @GetMapping(value = "/cart")
    public String howCartPage(Model model) {
        model.addAttribute("cart", cart);
        return "cart_page";
    }

    @GetMapping(value = "/cart/add/{id}")
    public String addProductToCart(@PathVariable(name = "id") Long id) {
        cart.addProduct(productService.getProductById(id));
        return "redirect:/";
    }

    @GetMapping(value = "/cart/remove/{id}")
    public String removeProductToCart(@PathVariable(name = "id") Long id) {
        cart.removeProductById(id);
        return "redirect:/cart";
    }

    @GetMapping(value = "/orders/create")
    public String showCreateOrderPage(Model model) {
        model.addAttribute("cart", cart);
        return "order_create";
    }

    @GetMapping(value = "/orders/confirm")
    public String showConfirmOrderPage(Model model, Principal principal) {
        model.addAttribute("sum", cart.getSum());
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        return "confirm_order";
    }

    @PostMapping(value = "/orders/save")
    public String saveOrder(@RequestParam(name = "address") String address
            , @RequestParam(name = "phone") String phone
            , @RequestParam(name = "recipient") String recipient
            , Principal principal) {
        orderService.saveOrUpdateOrder(new Order(cart, userService.findByUsername(principal.getName()), address, phone, recipient));
        return "redirect:/";
    }
}
