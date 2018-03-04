package uk.dangrew.nuts.apis.tesco.api;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import uk.dangrew.nuts.apis.tesco.item.TescoFoodReference;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;

public class TescoFoodItemProviderDummy {
   
   private final TescoFoodReference item1;
   private final TescoFoodReference item2;
   private final TescoFoodReference item3;
   
   private final List< Food > item1Options;
   private final List< Food > item2Options;
   private final List< Food > item3Options;

   public TescoFoodItemProviderDummy() {
      item1 = new TescoFoodReference( "Item 1" );
      item2 = new TescoFoodReference( "Item 2" );
      item3 = new TescoFoodReference( "Item 3" );
      
      item1Options = Arrays.asList(
               new FoodItem( "Item 1 (100g)" ),
               new FoodItem( "Item 1 (1 Serving)" )
      );
      item1Options.get( 0 ).properties().setMacros( 10, 15, 20 );
      item1Options.get( 1 ).properties().setMacros( 1, 1.5, 2 );
      
      item2Options = Arrays.asList(
               new FoodItem( "Item 2 (100g)" ),
               new FoodItem( "Item 2 (60g Portion)" )
      );
      item2Options.get( 0 ).properties().setMacros( 10, 15, 1 );
      item2Options.get( 1 ).properties().setMacros( 6, 9, 0.6 );
      
      item3Options = Arrays.asList(
               new FoodItem( "Item 1 (100g)" )
      );
      item3Options.get( 0 ).properties().setMacros( 45, 100, 0.9 );
      //hide behind cache
   }//End Constructor
   
   public List< TescoFoodReference > getFoodItems(){
      return Arrays.asList( 
               item1, item2, item3
      );
   }//End Method

   public List< Food > getOptionsFor( TescoFoodReference food ) {
      if ( food == item1 ) {
         return item1Options;
      } else if ( food == item2 ) {
         return item2Options;
      } else if ( food == item3 ) {
         return item3Options;
      } else {
         return Collections.emptyList();
      }
   }//End Method
   
}//End Class
