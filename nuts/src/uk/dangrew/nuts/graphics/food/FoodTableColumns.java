/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.food;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.graphics.table.FoodTable;
import uk.dangrew.nuts.graphics.table.FoodTableColumnsPopulator;
import uk.dangrew.nuts.graphics.table.FoodTableConfiguration;

/**
 * {@link FoodTableColumns} provides the {@link javafx.scene.control.TableColumn} configuration 
 * for a {@link FoodTable}.
 */
public class FoodTableColumns< FoodTypeT extends Food > implements FoodTableColumnsPopulator< FoodTypeT > {

   static final String COLUMN_TITLE_FOOD = "Food";
   static final String COLUMN_TITLE_CALORIES = "Calories";
   static final String COLUMN_TITLE_CALORIE_DENSITY = "Density";
   static final String COLUMN_TITLE_CARBS = "Carbohydrates";
   static final String COLUMN_TITLE_FATS = "Fats";
   static final String COLUMN_TITLE_PROTEINS = "Protein";
   static final String COLUMN_TITLE_CALORIES_PROPORTION = "Calories %";
   static final String COLUMN_TITLE_CARBS_PROPORTION = "Carbohydrates %";
   static final String COLUMN_TITLE_FATS_PROPORTION = "Fats %";
   static final String COLUMN_TITLE_PROTEINS_PROPORTION = "Protein %";
   
   private final FoodTableConfiguration configuration;

   /**
    * Constructs a new {@link FoodTable}.
    */
   public FoodTableColumns() {
      this.configuration = new FoodTableConfiguration();
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public void populateColumns( FoodTable< FoodTypeT > table ) {
      configuration.initialStringColumn( table, COLUMN_TITLE_FOOD, 0.3, FoodProperties::nameProperty );
      
      configuration.initialNutrientColumn( table, COLUMN_TITLE_CALORIES, 0.08, f -> f.properties().calories(), true );
      configuration.initialNutrientColumn( table, COLUMN_TITLE_CARBS, 0.08, f -> f.properties().carbohydrates(), true );
      configuration.initialNutrientColumn( table, COLUMN_TITLE_FATS, 0.08, f -> f.properties().fats(), true );
      configuration.initialNutrientColumn( table, COLUMN_TITLE_PROTEINS, 0.08, f -> f.properties().protein(), true );
      
      configuration.initialRatioColumn( table, COLUMN_TITLE_CALORIES_PROPORTION, 0.08, f -> f.goalAnalytics().caloriesRatioProperty() );
      configuration.initialRatioColumn( table, COLUMN_TITLE_CARBS_PROPORTION, 0.08, f -> f.goalAnalytics().carbohydratesRatioProperty() );
      configuration.initialRatioColumn( table, COLUMN_TITLE_FATS_PROPORTION, 0.08, f -> f.goalAnalytics().fatsRatioProperty() );
      configuration.initialRatioColumn( table, COLUMN_TITLE_PROTEINS_PROPORTION, 0.08, f -> f.goalAnalytics().proteinRatioProperty() );
   }//End Method
   
}//End Class
