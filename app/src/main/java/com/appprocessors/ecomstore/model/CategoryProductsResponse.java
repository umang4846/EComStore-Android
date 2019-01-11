package com.appprocessors.ecomstore.model;

import java.util.List;

public class CategoryProductsResponse {

  private List<CategoryProducts> categoryProducts;


  public List<CategoryProducts> getCategoryProducts() {
    return categoryProducts;
  }

  public void setCategoryProducts(List<CategoryProducts> categoryProducts) {
    this.categoryProducts = categoryProducts;
  }
}
