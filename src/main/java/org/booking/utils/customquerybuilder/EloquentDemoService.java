package org.booking.utils.customquerybuilder;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EloquentDemoService {

    public void demoUsage() {
        // Eloquent-style usage:

        // 1. Simple where
        @SuppressWarnings("unchecked")
        List<DemoProduct> phones = (List<DemoProduct>) (Object) DemoProduct.where("category", "Electronics").get();

        // 2. Chain conditions
        @SuppressWarnings("unchecked")
        List<DemoProduct> expensivePhones = (List<DemoProduct>) (Object) DemoProduct.where("category", "Electronics")
                .where("price", ">", 1000)
                .get();

        // 3. Order By
        @SuppressWarnings("unchecked")
        List<DemoProduct> sorted = (List<DemoProduct>) (Object) DemoProduct.query()
                .orderBy("name", "asc")
                .get();

        // 4. All
        List<DemoProduct> allProducts = DemoProduct.all();

        // 5. Select specific columns (Returns List<Object> which are Maps)
        List<Object> partialData = DemoProduct.select("name", "price")
                .where("category", "Electronics")
                .get();

        // Example handling:
        for (Object row : partialData) {
            java.util.Map<String, Object> map = (java.util.Map<String, Object>) row;
            System.out.println("Name: " + map.get("name"));
        }
    }
}
