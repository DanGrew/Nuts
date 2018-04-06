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
import uk.dangrew.nuts.goal.CalorieGoalStore;
import uk.dangrew.nuts.goal.proportion.ProportionGoalStore;
import uk.dangrew.nuts.graphics.table.ConceptOptionsImpl;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableColumnsPopulator;
import uk.dangrew.nuts.graphics.table.TableConfiguration;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.template.Template;

/**
 * {@link TemplateTableColumns} provides the {@link javafx.scene.control.TableColumn} configuration 
 * for a {@link TemplateTable}.
 */
public class TemplateTableColumns< FoodTypeT extends Template > implements ConceptTableColumnsPopulator< Template > {

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
   
   private final CalorieGoalStore goals;
   private final ProportionGoalStore proportionGoals;
   private final TableConfiguration configuration;

   public TemplateTableColumns( Database database ) {
      this.goals = database.calorieGoals();
      this.proportionGoals = database.proportionGoals();
      this.configuration = new TableConfiguration();
   }//End Constructor
   
   @Override public void populateColumns( ConceptTable< Template > table ) {
      configuration.initialiseFoodPropertyStringColumn( table, COLUMN_TITLE_TEMPLATE, 0.2, FoodProperties::nameProperty, true );
      configuration.initialiseFoodDropDownColumn( 
               table, 
               COLUMN_TITLE_GOAL, 
               0.1, 
               r -> r.concept().goalAnalytics().goal(), 
               ( r, v ) -> r.concept().goalAnalytics().goal().set( v ),
               new ConceptOptionsImpl<>( Arrays.asList( goals, proportionGoals ) )
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
