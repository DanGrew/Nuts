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
import uk.dangrew.nuts.cycle.Cycle;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableColumnsPopulator;
import uk.dangrew.nuts.graphics.table.TableConfiguration;

public class UiCycleTableColumns implements ConceptTableColumnsPopulator< Cycle > {

   static final String COLUMN_TITLE_CYCLE = "Cycle";
   
   private final TableConfiguration configuration;

   public UiCycleTableColumns() {
      this.configuration = new TableConfiguration();
   }//End Constructor
   
   @Override public void populateColumns( ConceptTable< Cycle > table ) {
      configuration.initialiseStringColumn( table, COLUMN_TITLE_CYCLE, 0.3, c -> c.properties().nameProperty(), true );
      configuration.initialiseStringColumn( table, "Base Goal", 0.3, c -> new SimpleObjectProperty<>( c.baseGoal().properties().nameProperty().get() ), false );
      configuration.initialiseStringColumn( table, "Type", 0.3, c -> new SimpleObjectProperty<>( c.getClass().getSimpleName() ), false );
   }//End Method
   
}//End Class
