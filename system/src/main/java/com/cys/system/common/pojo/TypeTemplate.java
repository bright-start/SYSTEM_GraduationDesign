package com.cys.system.common.pojo;

public class TypeTemplate {
    private Integer id;
    private String typeTemplateName;
    private String brands;
    private String specifications;
    private String customAttributeItem;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeTemplateName() {
        return typeTemplateName;
    }

    public void setTypeTemplateName(String typeTemplateName) {
        this.typeTemplateName = typeTemplateName;
    }

    public String getBrands() {
        return brands;
    }

    public void setBrands(String brands) {
        this.brands = brands;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getCustomAttributeItem() {
        return customAttributeItem;
    }

    public void setCustomAttributeItem(String customAttributeItem) {
        this.customAttributeItem = customAttributeItem;
    }
}
