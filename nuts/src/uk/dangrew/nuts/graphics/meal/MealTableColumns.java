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
      super( components );
      this.configuration = new TableConfiguration();
      this.conceptOptions = new ConceptOptionsImpl<>( Arrays.asList( 
               components.database().foodItems(), 
               components.database().meals() 
      ) );
   }//End Constructor
   
   @Override protected void changeColumns() {
      configuration.initialiseFoodDropDownColumn( 
               table(), 
               COLUMN_TITLE_FOOD, 
               COLUMN_WIDTH_FOOD, 
               r -> r.concept().food(), 
               ( r, v ) -> r.concept().setFood( v ), 
               conceptOptions 
      );
      
      configuration.initialisePortionColumn( table(), COLUMN_TITLE_PORTION, COLUMN_WIDTH_PORTION );
      
      double remainingWidth = 0.98 - COLUMN_WIDTH_FOOD - COLUMN_WIDTH_PORTION;
      columnsForShowing( table(), Food::nutrition, NutritionalUnit::name, remainingWidth );
      
      table().getColumns().forEach( c -> c.setSortable( false ) );
   }//End Method

}//End Class
