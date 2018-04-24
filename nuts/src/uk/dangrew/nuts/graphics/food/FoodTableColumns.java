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
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableColumnsPopulator;
import uk.dangrew.nuts.graphics.table.TableConfiguration;

/**
 * {@link FoodTableColumns} provides the {@link javafx.scene.control.TableColumn} configuration 
 * for a {@link FoodTable}.
 */
public class FoodTableColumns< FoodTypeT extends Food > implements ConceptTableColumnsPopulator< FoodTypeT > {

   static final String COLUMN_TITLE_FOOD = "Food";
   static final String COLUMN_TITLE_CALORIES = "Calories";
   static final String COLUMN_TITLE_CALORIE_DENSITY = "Density";
   static final String COLUMN_TITLE_CARBS = "Carbs";
   static final String COLUMN_TITLE_FIBER = "Fiber";
   static final String COLUMN_TITLE_FATS = "Fats";
   static final String COLUMN_TITLE_PROTEINS = "Protein";
   
   private final TableConfiguration configuration;

   /**
    * Constructs a new {@link FoodTableColumns}.
    */
   public FoodTableColumns() {
      this.configuration = new TableConfiguration();
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public void populateColumns( ConceptTable< FoodTypeT > table ) {
      configuration.initialiseFoodPropertyStringColumn( table, COLUMN_TITLE_FOOD, 0.3, FoodProperties::nameProperty, true );
      
      configuration.initialiseNutrientColumn( table, COLUMN_TITLE_CALORIES, 0.13, f -> f.properties().calories(), true );
      configuration.initialiseNutrientColumn( table, COLUMN_TITLE_CARBS, 0.13, f -> f.properties().carbohydrates(), true );
      configuration.initialiseNutrientColumn( table, COLUMN_TITLE_FATS, 0.13, f -> f.properties().fats(), true );
      configuration.initialiseNutrientColumn( table, COLUMN_TITLE_PROTEINS, 0.13, f -> f.properties().protein(), true );
      configuration.initialiseNutrientColumn( table, COLUMN_TITLE_FIBER, 0.13, f -> f.properties().fiber(), true );
   }//End Method
   
}//End Class
