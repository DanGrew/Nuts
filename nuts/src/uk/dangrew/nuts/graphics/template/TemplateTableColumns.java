/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.template;

import java.util.Arrays;

import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.goal.GoalStore;
import uk.dangrew.nuts.graphics.table.FoodOptions;
import uk.dangrew.nuts.graphics.table.FoodTable;
import uk.dangrew.nuts.graphics.table.FoodTableColumnsPopulator;
import uk.dangrew.nuts.graphics.table.TableConfiguration;
import uk.dangrew.nuts.template.Template;

/**
 * {@link TemplateTableColumns} provides the {@link javafx.scene.control.TableColumn} configuration 
 * for a {@link TemplateTable}.
 */
public class TemplateTableColumns< FoodTypeT extends Template > implements FoodTableColumnsPopulator< Template > {

   static final String COLUMN_TITLE_TEMPLATE = "Template";
   static final String COLUMN_TITLE_GOAL = "Goal";
   static final String COLUMN_TITLE_CALORIES = "Calories";
   static final String COLUMN_TITLE_CALORIE_DENSITY = "Density";
   static final String COLUMN_TITLE_CARBS = "Carbohydrates";
   static final String COLUMN_TITLE_FATS = "Fats";
   static final String COLUMN_TITLE_PROTEINS = "Protein";
   static final String COLUMN_TITLE_CALORIES_PROPORTION = "Calories %";
   static final String COLUMN_TITLE_CARBS_PROPORTION = "Carbohydrates %";
   static final String COLUMN_TITLE_FATS_PROPORTION = "Fats %";
   static final String COLUMN_TITLE_PROTEINS_PROPORTION = "Protein %";
   
   private final GoalStore goals;
   private final TableConfiguration configuration;

   /**
    * Constructs a new {@link FoodTable}.
    * @param goals the {@link GoalStore}.
    */
   public TemplateTableColumns( GoalStore goals ) {
      this.goals = goals;
      this.configuration = new TableConfiguration();
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public void populateColumns( FoodTable< Template > table ) {
      configuration.initialiseFoodProperyStringColumn( table, COLUMN_TITLE_TEMPLATE, 0.2, FoodProperties::nameProperty, true );
      configuration.initialiseFoodDropDownColumn( 
               table, 
               COLUMN_TITLE_GOAL, 
               0.1, 
               r -> r.food().goalAnalytics().goal(), 
               ( r, v ) -> r.food().goalAnalytics().goal().set( v ),
               new FoodOptions<>( Arrays.asList( goals ) )
      );

      configuration.initialiseNutrientColumn( table, COLUMN_TITLE_CALORIES, 0.08, f -> f.properties().calories(), true );
      configuration.initialiseNutrientColumn( table, COLUMN_TITLE_CARBS, 0.08, f -> f.properties().carbohydrates(), true );
      configuration.initialiseNutrientColumn( table, COLUMN_TITLE_FATS, 0.08, f -> f.properties().fats(), true );
      configuration.initialiseNutrientColumn( table, COLUMN_TITLE_PROTEINS, 0.08, f -> f.properties().protein(), true );
      
      configuration.initialiseRatioColumn( table, COLUMN_TITLE_CALORIES_PROPORTION, 0.08, f -> f.goalAnalytics().caloriesRatioProperty() );
      configuration.initialiseRatioColumn( table, COLUMN_TITLE_CARBS_PROPORTION, 0.08, f -> f.goalAnalytics().carbohydratesRatioProperty() );
      configuration.initialiseRatioColumn( table, COLUMN_TITLE_FATS_PROPORTION, 0.08, f -> f.goalAnalytics().fatsRatioProperty() );
      configuration.initialiseRatioColumn( table, COLUMN_TITLE_PROTEINS_PROPORTION, 0.08, f -> f.goalAnalytics().proteinRatioProperty() );
   }//End Method
   
}//End Class
