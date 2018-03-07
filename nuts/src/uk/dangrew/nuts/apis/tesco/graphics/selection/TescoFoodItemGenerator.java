package uk.dangrew.nuts.apis.tesco.graphics.selection;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import uk.dangrew.nuts.apis.tesco.item.TescoFoodDescription;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;

public class TescoFoodItemGenerator {

   static final String PER_100_SUFFIX = " (per 100)";

   public List< Food > generateFoodItemsFor( TescoFoodDescription description ) {
      List< Food > tescoItems = new ArrayList<>();
      applyPer100Item( description, tescoItems );
      return tescoItems;
   }//End Method
   
   private void applyPer100Item( TescoFoodDescription description, List< Food > items ) {
      if ( description.groceryProperties().name().get() == null ) {
         return;
      }
      FoodItem item = new FoodItem( description.groceryProperties().name().get() + PER_100_SUFFIX );
      convertAndSetProperty( description.productDetail().nutrition().energyInKcal().valuePer100(), item.properties().calories() );
      convertAndSetProperty( description.productDetail().nutrition().carbohydrates().valuePer100(), item.properties().carbohydrates() );
      convertAndSetProperty( description.productDetail().nutrition().fat().valuePer100(), item.properties().fats() );
      convertAndSetProperty( description.productDetail().nutrition().protein().valuePer100(), item.properties().protein() );
      items.add( item );
   }//End Method
   
   private void convertAndSetProperty( ObjectProperty< String > nutrition, ObjectProperty< Double > value ) {
      String nutritionString = nutrition.get();
      if ( nutritionString == null ) {
         value.set( 0.0 );
         return;
      }
      
      try {
         double conversion = Double.parseDouble( nutritionString );
         value.set( conversion );
      } catch ( NumberFormatException exception ) {
         value.set( 0.0 );
         return;
      }
   }//End Method

}//End Constructor
