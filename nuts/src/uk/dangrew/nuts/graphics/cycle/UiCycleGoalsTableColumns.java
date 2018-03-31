/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.cycle;

import javafx.beans.property.SimpleObjectProperty;
import uk.dangrew.nuts.goal.DerivedCalorieGoal;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableColumnsPopulator;
import uk.dangrew.nuts.graphics.table.TableConfiguration;

public class UiCycleGoalsTableColumns implements ConceptTableColumnsPopulator< DerivedCalorieGoal > {

   private final TableConfiguration configuration;

   public UiCycleGoalsTableColumns() {
      this.configuration = new TableConfiguration();
   }//End Constructor
   
   @Override public void populateColumns( ConceptTable< DerivedCalorieGoal > table ) {
      configuration.initialiseStringColumn( table, "Goal", 0.3, c -> c.properties().nameProperty(), true );
      configuration.initialiseStringColumn( table, "Base Goal", 0.3, c -> new SimpleObjectProperty<>( c.baseGoal().properties().nameProperty().get() ), false );
      configuration.initialiseDoubleColumn( table, "Calorie Offset", 0.3, c -> c.concept().calorieOffset(), true );
   }//End Method
   
}//End Class
