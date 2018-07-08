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

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.goal.calorie.CalorieGoalStore;
import uk.dangrew.nuts.goal.proportion.ProportionGoalStore;
import uk.dangrew.nuts.graphics.food.FoodTableColumns;
import uk.dangrew.nuts.graphics.table.ConceptOptionsImpl;
import uk.dangrew.nuts.graphics.table.TableComponents;
import uk.dangrew.nuts.graphics.table.TableConfiguration;
import uk.dangrew.nuts.graphics.table.configuration.TableColumnWidths;
import uk.dangrew.nuts.graphics.table.configuration.TableViewColumnConfigurer;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.template.Template;

/**
 * {@link TemplateTableColumns} provides the {@link javafx.scene.control.TableColumn} configuration 
 * for a {@link TemplateTable}.
 */
public class TemplateTableColumns extends FoodTableColumns< Template > {

   static final String COLUMN_TITLE_TEMPLATE = "Template";
   static final String COLUMN_TITLE_GOAL = "Goal";
   
   static final double COLUMN_WIDTH_TEMPLATE = 0.2;
   static final double COLUMN_WIDTH_GOAL = 0.1;
   
   private final CalorieGoalStore goals;
   private final ProportionGoalStore proportionGoals;
   private final TableConfiguration configuration;

   public TemplateTableColumns( TableComponents< Template > components ) {
      super(
               new TableColumnWidths()
                  .withFoodNameWidth( 0.2 )
                  .withGoalWidth( 0.1 )
                  .withCombinedUnitWidth( 0.35 ),
               components 
      );
      this.goals = components.database().calorieGoals();
      this.proportionGoals = components.database().proportionGoals();
      this.configuration = new TableConfiguration();
   }//End Constructor
   
   @Override protected void changeColumns() {
      standardNameColumn( table(), COLUMN_TITLE_TEMPLATE, COLUMN_WIDTH_TEMPLATE );
      configuration.initialiseFoodDropDownColumn( 
               new TableViewColumnConfigurer<>( table() ), 
               COLUMN_TITLE_GOAL, 
               COLUMN_WIDTH_GOAL, 
               r -> r.goalAnalytics().goal(), 
               ( r, v ) -> r.goalAnalytics().goal().set( v ),
               new ConceptOptionsImpl<>( Arrays.asList( goals, proportionGoals ) )
      );

      configuration.configureVisibleNutrientUnitColumns( 
               () -> new TableViewColumnConfigurer<>( table() ),
               tableWidths(),
               Food::nutrition, 
               NutritionalUnit::displayName, 
               false,
               settings() 
      );
      configuration.configureVisibleNutrientUnitColumns( 
               () -> new TableViewColumnConfigurer<>( table() ),
               tableWidths(),
               Template::goalAnalytics, 
               u -> u.displayName() + " %", 
               false,
               settings() 
      );
   }//End Method
   
}//End Class
