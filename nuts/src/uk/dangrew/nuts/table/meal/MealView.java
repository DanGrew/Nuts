/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.table.meal;

import javafx.scene.layout.BorderPane;
import uk.dangrew.nuts.store.Database;

/**
 * {@link MealView} provides a {@link MealTable} with {@link MealSummary}.
 */
public class MealView extends BorderPane {

   private final MealTable table;
   private final MealSummary summary;
   
   /**
    * Constructs a new {@link MealView}.
    * @param database the {@link Database}.
    */
   public MealView( Database database ) {
      table = new MealTable( database );
      setCenter( table );
      setBottom( summary = new MealSummary() );
   }//End Constructor

   /**
    * Access to the {@link MealTable}.
    * @return the {@link MealTable}.
    */
   public MealTable table() {
      return table;
   }//End Method
   
   /**
    * Access to the {@link MealSummary}.
    * @return the {@link MealSummary}.
    */
   public MealSummary mealSummary(){
      return summary;
   }//End Method
   
}//End Class
