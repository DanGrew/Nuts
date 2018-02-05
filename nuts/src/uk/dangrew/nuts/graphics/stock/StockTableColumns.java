/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.stock;

import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableColumnsPopulator;
import uk.dangrew.nuts.graphics.table.TableConfiguration;

/**
 * {@link MealTableColumns} provides the {@link TableColumn} configuration for a {@link MealTable}.
 */
public class StockTableColumns implements ConceptTableColumnsPopulator< FoodPortion > {

   static final String COLUMN_TITLE_FOOD = "Food";
   static final String COLUMN_TITLE_PORTION = "Portion %";
   
   private final TableConfiguration configuration;

   /**
    * Constructs a new {@link MealTableColumns}.
    * @param database the {@link Database} for the {@link uk.dangrew.nuts.meal.Meal} {@link uk.dangrew.nuts.system.Concept}s.
    */
   public StockTableColumns() {
      this.configuration = new TableConfiguration();
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public void populateColumns( ConceptTable< FoodPortion > table ) {
      configuration.initialiseStringColumn( table, COLUMN_TITLE_FOOD, 0.5, r -> r.concept().food().get().properties().nameProperty().get() );
      configuration.initialisePortionColumn( table, COLUMN_TITLE_PORTION, 0.5 );
      
      table.getColumns().forEach( c -> c.setSortable( false ) );
   }//End Method

}//End Class
