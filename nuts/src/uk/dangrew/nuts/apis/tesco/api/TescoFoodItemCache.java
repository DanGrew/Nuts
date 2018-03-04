package uk.dangrew.nuts.apis.tesco.api;

import java.util.ArrayList;
import java.util.List;

import uk.dangrew.nuts.apis.tesco.item.TescoFoodReference;
import uk.dangrew.nuts.food.Food;

public class TescoFoodItemCache {

   private final TescoFoodItemProviderDummy provider;
   private final List< TescoFoodReference > items;
   
   public TescoFoodItemCache() {
      //system wide object
      //interact with database
      this.provider = new TescoFoodItemProviderDummy();
      this.items = new ArrayList<>();
      this.items.addAll( provider.getFoodItems() );
   }//End Constructor
   
   public List< TescoFoodReference > getFoodItems(){
      return items;
   }//End Method
   
   public List< Food > getOptionsFor( TescoFoodReference food ) {
      return provider.getOptionsFor( food );
   }
}//End Class
