package ken.vivid.product.config;

import ken.vivid.product.application.port.out.product.DeleteProductPort;
import ken.vivid.product.application.port.out.product.LoadProductPort;
import ken.vivid.product.application.port.out.product.SaveProductPort;
import ken.vivid.product.application.port.out.product.SaveProductRegistrationPort;
import ken.vivid.product.application.port.out.stock.LoadStockPort;
import ken.vivid.product.application.port.out.stock.SaveStockMovementPort;
import ken.vivid.product.application.port.out.stock.SaveStockPort;
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
    public ProductService productService(LoadProductPort loadProductPort,
                                         SaveProductPort saveProductPort,
                                         DeleteProductPort deleteProductPort,
                                         LoadStockPort loadStockPort) {
        return new ProductService(loadProductPort, saveProductPort, deleteProductPort, loadStockPort);
    }

    @Bean
    public StockService stockService(LoadProductPort loadProductPort,
                                     LoadStockPort loadStockPort,
                                     SaveStockPort saveStockPort,
                                     SaveProductRegistrationPort saveProductRegistrationPort,
                                     SaveStockMovementPort saveStockMovementPort,
                                     StockAllocationPolicy stockAllocationPolicy) {
        return new StockService(loadProductPort, loadStockPort, saveStockPort,
                saveProductRegistrationPort, saveStockMovementPort, stockAllocationPolicy);
    }
}