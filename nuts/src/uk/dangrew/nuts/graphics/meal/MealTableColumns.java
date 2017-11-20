/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.meal;

import java.util.Arrays;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.table.FoodOptions;
import uk.dangrew.nuts.graphics.table.FoodTable;
import uk.dangrew.nuts.graphics.table.FoodTableColumnsPopulator;
import uk.dangrew.nuts.graphics.table.TableConfiguration;
import uk.dangrew.nuts.store.Database;

/**
 * {@link MealTableColumns} provides the {@link TableColumn} configuration for a {@link MealTable}.
 */
public class MealTableColumns implements FoodTableColumnsPopulator< FoodPortion > {

   static final String COLUMN_TITLE_FOOD = "Food";
   static final String COLUMN_TITLE_CALORIES = "Calories";
   static final String COLUMN_TITLE_CALORIE_DENSITY = "Density";
   static final String COLUMN_TITLE_PORTION = "Portion %";
   static final String COLUMN_TITLE_CARBS = "Carbs";
   static final String COLUMN_TITLE_FATS = "Fats";
   static final String COLUMN_TITLE_PROTEINS = "Protein";
   static final String COLUMN_TITLE_CALORIES_PROPORTION = "Calories %";
   static final String COLUMN_TITLE_CARBS_PROPORTION = "Carbs %";
   static final String COLUMN_TITLE_FATS_PROPORTION = "Fats %";
   static final String COLUMN_TITLE_PROTEINS_PROPORTION = "Protein %";
   
   private final FoodOptions< Food > foodOptions;
   private final TableConfiguration configuration;

   /**
    * Constructs a new {@link MealTableColumns}.
    * @param database the {@link Database} for the {@link uk.dangrew.nuts.meal.Meal} {@link Food}s.
    */
   public MealTableColumns( Database database ) {
      this.configuration = new TableConfiguration();
      this.foodOptions = new FoodOptions<>( Arrays.asList( database.foodItems(), database.meals() ) );
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public void populateColumns( FoodTable< FoodPortion > table ) {
      configuration.initialiseFoodDropDownColumn( 
               table, 
               COLUMN_TITLE_FOOD, 
               0.25, 
               r -> r.food().food(), 
               ( r, v ) -> r.food().setFood( v ), 
               foodOptions 
      );
      
      configuration.initialisePortionColumn( table, COLUMN_TITLE_PORTION, 0.05 );
      
      configuration.initialiseNutrientColumn( table, COLUMN_TITLE_CALORIES, 0.16, f -> f.properties().calories(), false );
      configuration.initialiseNutrientColumn( table, COLUMN_TITLE_CARBS, 0.16, f -> f.properties().carbohydrates(), false );
      configuration.initialiseNutrientColumn( table, COLUMN_TITLE_FATS, 0.16, f -> f.properties().fats(), false );
      configuration.initialiseNutrientColumn( table, COLUMN_TITLE_PROTEINS, 0.16, f -> f.properties().protein(), false );
      
      table.getColumns().forEach( c -> c.setSortable( false ) );
   }//End Method

}//End Class
