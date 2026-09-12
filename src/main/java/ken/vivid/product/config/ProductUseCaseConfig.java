package ken.vivid.product.config;

import ken.vivid.product.application.port.out.product.DeleteProduct;
import ken.vivid.product.application.port.out.product.LoadProduct;
import ken.vivid.product.application.port.out.product.SaveProduct;
import ken.vivid.product.application.port.out.product.SaveProductRegistration;
import ken.vivid.product.application.port.out.stock.LoadStock;
import ken.vivid.product.application.port.out.stock.SaveStockMovement;
import ken.vivid.product.application.port.out.stock.SaveStock;
import ken.vivid.product.application.service.ProductService;
import ken.vivid.product.application.service.StockAllocationPolicy;
import ken.vivid.product.application.service.StockService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductUseCaseConfig {

    @Bean
    public StockAllocationPolicy stockAllocationPolicy() {
        return new StockAllocationPolicy();
    }

    @Bean
    public ProductService productService(LoadProduct loadProduct,
                                         SaveProduct saveProduct,
                                         DeleteProduct deleteProduct,
                                         LoadStock loadStock) {
        return new ProductService(loadProduct, saveProduct, deleteProduct, loadStock);
    }

    @Bean
    public StockService stockService(LoadProduct loadProduct,
                                     LoadStock loadStock,
                                     SaveStock saveStock,
                                     SaveProductRegistration saveProductRegistration,
                                     SaveStockMovement saveStockMovement,
                                     StockAllocationPolicy stockAllocationPolicy) {
        return new StockService(loadProduct, loadStock, saveStock,
                saveProductRegistration, saveStockMovement, stockAllocationPolicy);
    }
}