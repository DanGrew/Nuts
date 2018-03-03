package uk.dangrew.nuts.apis.tesco.api;

import java.util.ArrayList;
import java.util.List;

import uk.dangrew.nuts.apis.tesco.item.TescoFoodItem;
import uk.dangrew.nuts.food.Food;

public class TescoFoodItemCache {

   private final TescoFoodItemProvider provider;
   private final List< TescoFoodItem > items;
   
   public TescoFoodItemCache() {
      //system wide object
      //interact with database
      this.provider = new TescoFoodItemProvider();
      this.items = new ArrayList<>();
      this.items.addAll( provider.getFoodItems() );
   }//End Constructor
   
   public List< TescoFoodItem > getFoodItems(){
      return items;
   }//End Method
   
   public List< Food > getOptionsFor( TescoFoodItem food ) {
      return provider.getOptionsFor( food );
   }
}//End Class
