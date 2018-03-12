package uk.dangrew.nuts.apis.tesco.model;

import uk.dangrew.nuts.system.Properties;

public class TescoFoodDescription extends TescoFoodReference {

   private final GroceryProperties groceryProperties;
   private final ProductDetail productDetail;
   
   public TescoFoodDescription( String name ) {
      this( new Properties( name ) );
   }//End Constructor
   
   public TescoFoodDescription( String id, String name ) {
      this( new Properties( id, name ) );
   }//End Constructor
   
   TescoFoodDescription( Properties properties ) {
      super( properties );
      this.groceryProperties = new GroceryProperties();
      this.productDetail = new ProductDetail();
   }//End Constructor
   
   public GroceryProperties groceryProperties(){
      return groceryProperties;
   }//End Method
   
   public ProductDetail productDetail(){
      return productDetail;
   }//End Method

}//End Class
