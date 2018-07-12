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
import uk.dangrew.nuts.graphics.food.FoodTableColumns;
import uk.dangrew.nuts.graphics.table.ConceptOptions;
import uk.dangrew.nuts.graphics.table.ConceptOptionsImpl;
import uk.dangrew.nuts.graphics.table.TableComponents;
import uk.dangrew.nuts.graphics.table.TableConfiguration;
import uk.dangrew.nuts.graphics.table.configuration.TableColumnWidths;
import uk.dangrew.nuts.graphics.table.configuration.TableViewColumnConfigurer;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

/**
 * {@link MealTableColumns} provides the {@link TableColumn} configuration for a {@link MealTable}.
 */
public class MealTableColumns extends FoodTableColumns< FoodPortion > {

   static final double COLUMN_WIDTH_PORTION = 0.10;
   static final double COLUMN_WIDTH_FOOD = 0.25;
   
   static final String COLUMN_TITLE_PORTION = "Portion %";
   
   private final ConceptOptions< Food > conceptOptions;
   private final TableConfiguration configuration;

   public MealTableColumns( TableComponents< FoodPortion > components ) {
      super( 
               new TableColumnWidths()
                  .withFoodNameWidth( 0.25 )
                  .withPortionWidth( 0.10 )
                  .withCombinedUnitWidth( 0.6 ), 
               components 
      );
      this.configuration = new TableConfiguration();
      this.conceptOptions = new ConceptOptionsImpl<>( Arrays.asList( 
               components.database().foodItems(), 
               components.database().meals() 
      ) );
   }//End Constructor
   
   @Override protected void changeColumns() {
      configuration.initialiseFoodDropDownColumn( 
               new TableViewColumnConfigurer<>( table() ), 
               COLUMN_TITLE_FOOD, 
               COLUMN_WIDTH_FOOD, 
               FoodPortion::food, 
               ( r, v ) -> r.setFood( v ), 
               conceptOptions 
      );
      
      configuration.initialisePortionColumn( 
               new TableViewColumnConfigurer<>( table() ), 
               COLUMN_TITLE_PORTION, 
               COLUMN_WIDTH_PORTION 
      );
      
      configuration.configureVisibleNutrientUnitColumns( 
               () -> new TableViewColumnConfigurer<>( table() ),
               tableWidths(),
               Food::nutrition, 
               NutritionalUnit::displayName, 
               false,
               settings()
               
      );
      
      table().getColumns().forEach( c -> c.setSortable( false ) );
   }//End Method

}//End Class
