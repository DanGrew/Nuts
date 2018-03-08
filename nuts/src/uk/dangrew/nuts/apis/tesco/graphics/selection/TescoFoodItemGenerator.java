package uk.dangrew.nuts.apis.tesco.graphics.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javafx.beans.property.ObjectProperty;
import uk.dangrew.nuts.apis.tesco.item.CalculatedNutrition;
import uk.dangrew.nuts.apis.tesco.item.TescoFoodDescription;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;

public class TescoFoodItemGenerator {
   
   private final TescoStringParser parser;
   
   public TescoFoodItemGenerator() {
      this( new TescoStringParser() );
   }//End Constructor

   TescoFoodItemGenerator( TescoStringParser parser ) {
      this.parser = parser;
   }//End Constructor

   public List< Food > generateFoodItemsFor( TescoFoodDescription description ) {
      List< Food > tescoItems = new ArrayList<>();
      if ( !sufficientDataFor( description, d -> d.groceryProperties().name() ) ) {
         return tescoItems;
      }
      
      applyPer100Item( description, tescoItems );
      applyPerServingItem( description, tescoItems );
      return tescoItems;
   }//End Method
   
   private void applyPer100Item( TescoFoodDescription description, List< Food > items ) {
      if ( !sufficientDataFor( description, d -> d.productDetail().nutrition().per100Header() ) ) {
         return;
      }
      
      CalculatedNutrition nutrition = description.productDetail().nutrition();
      
      String header = parser.parsePer100Header( nutrition.per100Header().get() );
      FoodItem item = generateFoodItem( description.groceryProperties().name().get(), header );
      convertAndSetProperty( nutrition.energyInKcal().valuePer100(), item.properties().calories() );
      convertAndSetProperty( nutrition.carbohydrates().valuePer100(), item.properties().carbohydrates() );
      convertAndSetProperty( nutrition.fat().valuePer100(), item.properties().fats() );
      convertAndSetProperty( nutrition.protein().valuePer100(), item.properties().protein() );
      items.add( item );
   }//End Method
   
   private void applyPerServingItem( TescoFoodDescription description, List< Food > items ) {
      if ( !sufficientDataFor( description, d -> d.productDetail().nutrition().perServingHeader() ) ) {
         return;
      }
      CalculatedNutrition nutrition = description.productDetail().nutrition();
      
      String header = parser.parsePerServingHeader( nutrition.perServingHeader().get() );
      FoodItem item = generateFoodItem( description.groceryProperties().name().get(), header );
      convertAndSetProperty( nutrition.energyInKcal().valuePerServing(), item.properties().calories() );
      convertAndSetProperty( nutrition.carbohydrates().valuePerServing(), item.properties().carbohydrates() );
      convertAndSetProperty( nutrition.fat().valuePerServing(), item.properties().fats() );
      convertAndSetProperty( nutrition.protein().valuePerServing(), item.properties().protein() );
      items.add( item );
   }//End Method
   
   private boolean sufficientDataFor( 
            TescoFoodDescription description, 
            Function< TescoFoodDescription, ObjectProperty< ?  > > propertyRetriever 
   ) {
      return propertyRetriever.apply( description ).get() != null;
   }//End Method
   
   private FoodItem generateFoodItem( String name, String header ) {
      return new FoodItem( 
               new StringBuilder()
                  .append( name )
                  .append( " (" ).append( header ).append( ")" )
                  .toString() 
      );
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
