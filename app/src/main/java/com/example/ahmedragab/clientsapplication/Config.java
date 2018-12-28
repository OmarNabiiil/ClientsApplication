package com.example.ahmedragab.clientsapplication;

public class Config {

    public static String IP = "192.168.1.47";
    public static String LOGIN_URL = "http://"+IP+"/supplyChain/loginUser.php";
    public static String PRODUCTS_URL = "http://"+IP+"/supplyChain/getAllProductsClients.php";
    public static String ADDTOCART_URL = "http://"+IP+"/supplyChain/add_to_cart.php";
    public static String CARTITEMS_URL = "http://"+IP+"/supplyChain/get_all_products_cart.php";
    public static String SUBMITORDER_URL = "http://"+IP+"/supplyChain/submit_order.php";

}
