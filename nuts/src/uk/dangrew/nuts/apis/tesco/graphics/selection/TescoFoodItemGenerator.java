package uk.dangrew.nuts.apis.tesco.graphics.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javafx.beans.property.ObjectProperty;
import uk.dangrew.nuts.apis.tesco.model.api.CalculatedNutrition;
import uk.dangrew.nuts.apis.tesco.model.nuts.TescoFoodDescription;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

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
      convertAndSetProperty( nutrition.energyInKcal().valuePer100(), NutritionalUnit.Calories.of( item ).property() );
      convertAndSetProperty( nutrition.carbohydrates().valuePer100(), NutritionalUnit.Carbohydrate.of( item ).property() );
      convertAndSetProperty( nutrition.fat().valuePer100(), NutritionalUnit.Fat.of( item ).property() );
      convertAndSetProperty( nutrition.protein().valuePer100(), NutritionalUnit.Protein.of( item ).property() );
      convertAndSetProperty( nutrition.fibre().valuePer100(), NutritionalUnit.Fibre.of( item ).property() );
      
      convertAndSetProperty( nutrition.calcium().valuePer100(), NutritionalUnit.Calcium.of( item ).property() );
      convertAndSetProperty( nutrition.folicAcid().valuePer100(), NutritionalUnit.FolicAcid.of( item ).property() );
      convertAndSetProperty( nutrition.iron().valuePer100(), NutritionalUnit.Iron.of( item ).property() );
      convertAndSetProperty( nutrition.niacin().valuePer100(), NutritionalUnit.Niacin.of( item ).property() );
      convertAndSetProperty( nutrition.omega3().valuePer100(), NutritionalUnit.Omega3.of( item ).property() );
      convertAndSetProperty( nutrition.riboflavin().valuePer100(), NutritionalUnit.Riboflavin.of( item ).property() );
      convertAndSetProperty( nutrition.salt().valuePer100(), NutritionalUnit.Salt.of( item ).property() );
      convertAndSetProperty( nutrition.saturates().valuePer100(), NutritionalUnit.SaturatedFat.of( item ).property() );
      convertAndSetProperty( nutrition.sugars().valuePer100(), NutritionalUnit.CarbohydrateSugars.of( item ).property() );
      convertAndSetProperty( nutrition.thiamin().valuePer100(), NutritionalUnit.Thiamin.of( item ).property() );
      items.add( item );
   }//End Method
   
   private void applyPerServingItem( TescoFoodDescription description, List< Food > items ) {
      if ( !sufficientDataFor( description, d -> d.productDetail().nutrition().perServingHeader() ) ) {
         return;
      }
      CalculatedNutrition nutrition = description.productDetail().nutrition();
      
      String header = parser.parsePerServingHeader( nutrition.perServingHeader().get() );
      FoodItem item = generateFoodItem( description.groceryProperties().name().get(), header );
      convertAndSetProperty( nutrition.energyInKcal().valuePerServing(), NutritionalUnit.Calories.of( item ).property() );
      convertAndSetProperty( nutrition.carbohydrates().valuePerServing(), NutritionalUnit.Carbohydrate.of( item ).property() );
      convertAndSetProperty( nutrition.fat().valuePerServing(), NutritionalUnit.Fat.of( item ).property() );
      convertAndSetProperty( nutrition.protein().valuePerServing(), NutritionalUnit.Protein.of( item ).property() );
      convertAndSetProperty( nutrition.fibre().valuePerServing(), NutritionalUnit.Fibre.of( item ).property() );
      
      convertAndSetProperty( nutrition.calcium().valuePerServing(), NutritionalUnit.Calcium.of( item ).property() );
      convertAndSetProperty( nutrition.folicAcid().valuePerServing(), NutritionalUnit.FolicAcid.of( item ).property() );
      convertAndSetProperty( nutrition.iron().valuePerServing(), NutritionalUnit.Iron.of( item ).property() );
      convertAndSetProperty( nutrition.niacin().valuePerServing(), NutritionalUnit.Niacin.of( item ).property() );
      convertAndSetProperty( nutrition.omega3().valuePerServing(), NutritionalUnit.Omega3.of( item ).property() );
      convertAndSetProperty( nutrition.riboflavin().valuePerServing(), NutritionalUnit.Riboflavin.of( item ).property() );
      convertAndSetProperty( nutrition.salt().valuePerServing(), NutritionalUnit.Salt.of( item ).property() );
      convertAndSetProperty( nutrition.saturates().valuePerServing(), NutritionalUnit.SaturatedFat.of( item ).property() );
      convertAndSetProperty( nutrition.sugars().valuePerServing(), NutritionalUnit.CarbohydrateSugars.of( item ).property() );
      convertAndSetProperty( nutrition.thiamin().valuePerServing(), NutritionalUnit.Thiamin.of( item ).property() );
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
         //trim string down to value only
         double conversion = Double.parseDouble( nutritionString );
         value.set( conversion );
      } catch ( NumberFormatException exception ) {
         value.set( 0.0 );
         return;
      }
   }//End Method

}//End Constructor
