/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.goal;

import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.goal.CalorieGoal;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableColumnsPopulator;
import uk.dangrew.nuts.graphics.table.TableConfiguration;

/**
 * {@link GoalTableColumns} provides the {@link javafx.scene.control.TableColumn} configuration 
 * for a {@link GoalTable}.
 */
public class GoalTableColumns implements ConceptTableColumnsPopulator< CalorieGoal > {

   static final String COLUMN_TITLE_FOOD = "Goal";
   static final String COLUMN_TITLE_CALORIES = "Calories";
   static final String COLUMN_TITLE_CARBS = "Carbohydrates";
   static final String COLUMN_TITLE_FATS = "Fats";
   static final String COLUMN_TITLE_PROTEINS = "Protein";
   
   private final TableConfiguration configuration;

   /**
    * Constructs a new {@link GoalTableColumns}.
    */
   public GoalTableColumns() {
      this.configuration = new TableConfiguration();
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public void populateColumns( ConceptTable< CalorieGoal > table ) {
      configuration.initialiseFoodPropertyStringColumn( table, COLUMN_TITLE_FOOD, 0.3, FoodProperties::nameProperty, true );
      
      configuration.initialiseNutrientColumn( table, COLUMN_TITLE_CALORIES, 0.16, f -> f.properties().calories(), true );
      configuration.initialiseNutrientColumn( table, COLUMN_TITLE_CARBS, 0.16, f -> f.properties().carbohydrates(), true );
      configuration.initialiseNutrientColumn( table, COLUMN_TITLE_FATS, 0.16, f -> f.properties().fats(), true );
      configuration.initialiseNutrientColumn( table, COLUMN_TITLE_PROTEINS, 0.16, f -> f.properties().protein(), true );
   }//End Method
   
}//End Class
