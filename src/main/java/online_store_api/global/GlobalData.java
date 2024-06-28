package online_store_api.global;

import online_store_api.entities.Products;

import java.util.ArrayList;
import java.util.List;

public class GlobalData {
    public static List<Products> cart;

    static {
        cart = new ArrayList<>();
    }
}
