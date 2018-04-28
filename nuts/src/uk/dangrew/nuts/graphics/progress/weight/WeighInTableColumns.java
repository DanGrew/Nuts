/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.progress.weight;

import uk.dangrew.nuts.graphics.table.TableConfiguration;

/**
 * {@link WeighInTableColumns} provides the {@link javafx.scene.control.TableColumn} configuration 
 * for a {@link WeighInTable}.
 */
public class WeighInTableColumns {

   protected static final String COLUMN_TITLE_DATE = "Date";
   protected static final String COLUMN_TITLE_M_WEIGHT = "Morning\nWeight (lbs)";
   protected static final String COLUMN_TITLE_M_BODY_FAT = "Morning\nBody Fat %";
   protected static final String COLUMN_TITLE_M_LEAN_MASS = "Morning\nLean Mass (lbs)";
   protected static final String COLUMN_TITLE_E_WEIGHT = "Evening\nWeight (lbs)";
   protected static final String COLUMN_TITLE_E_BODY_FAT = "Evening\nBody Fat %";
   protected static final String COLUMN_TITLE_E_LEAN_MASS = "Evening\nLean Mass (lbs)";
   protected static final String COLUMN_TITLE_A_WEIGHT = "Average\nWeight (lbs)";
   protected static final String COLUMN_TITLE_A_BODY_FAT = "Average\nBody Fat %";
   protected static final String COLUMN_TITLE_A_LEAN_MASS = "Average\nLean Mass (lbs)";
   
   private final TableConfiguration configuration;

   /**
    * Constructs a new {@link WeighInTableColumns}.
    */
   public WeighInTableColumns() {
      this.configuration = new TableConfiguration();
   }//End Constructor
   
   /**
    * Method to populate the columns of the {@link WeighInTable}.
    * @param table the {@link WeighInTable}.
    */
   public void populateColumns( WeighInTable table ) {
      configuration.initialiseStringColumn( table, COLUMN_TITLE_DATE, 0.1, r -> r.recording().date().toString() );
      
      configuration.initialiseDoubleColumn( table, COLUMN_TITLE_M_WEIGHT, 0.1, r -> r.recording().morningWeighIn().weight(), true );
      configuration.initialiseDoubleColumn( table, COLUMN_TITLE_M_BODY_FAT, 0.1, r -> r.recording().morningWeighIn().bodyFat(), true );
      configuration.initialiseDoubleColumn( table, COLUMN_TITLE_M_LEAN_MASS, 0.1, r -> r.recording().morningWeighIn().leanMass(), true );
      
      configuration.initialiseDoubleColumn( table, COLUMN_TITLE_E_WEIGHT, 0.1, r -> r.recording().eveningWeighIn().weight(), true );
      configuration.initialiseDoubleColumn( table, COLUMN_TITLE_E_BODY_FAT, 0.1, r -> r.recording().eveningWeighIn().bodyFat(), true );
      configuration.initialiseDoubleColumn( table, COLUMN_TITLE_E_LEAN_MASS, 0.1, r -> r.recording().eveningWeighIn().leanMass(), true );
      
      configuration.initialiseDoubleColumn( table, COLUMN_TITLE_A_WEIGHT, 0.1, r -> r.recording().runningAverage().weight(), false );
      configuration.initialiseDoubleColumn( table, COLUMN_TITLE_A_BODY_FAT, 0.1, r -> r.recording().runningAverage().bodyFat(), false );
      configuration.initialiseDoubleColumn( table, COLUMN_TITLE_A_LEAN_MASS, 0.1, r -> r.recording().runningAverage().leanMass(), false );
   }//End Method
   
}//End Class
