package contract.entities;

import lombok.Data;

@Data
public class ProductDTO {
    
    private Long id;
    private Long categoryId;
    private String vendorCode;
    private String title;
    private String shortDescription;
    private String fullDescription;
    private Double price;
}
