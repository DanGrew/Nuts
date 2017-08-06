/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.progress;

import uk.dangrew.nuts.graphics.table.TableConfiguration;

/**
 * {@link WeighInTableColumns} provides the {@link javafx.scene.control.TableColumn} configuration 
 * for a {@link WeighInTable}.
 */
public class WeighInTableColumns {

   protected static final String COLUMN_TITLE_DATE = "Date";
   protected static final String COLUMN_TITLE_DAY_TIME = "Day Time";
   protected static final String COLUMN_TITLE_WEIGHT = "Weight (lbs)";
   protected static final String COLUMN_TITLE_BODY_FAT = "Body Fat %";
   protected static final String COLUMN_TITLE_LEAN_MASS = "Lean Mass (lbs)";
   protected static final String COLUMN_TITLE_TYPE = "Type";
   
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
      configuration.initialiseStringColumn( table, COLUMN_TITLE_DATE, 0.3, r -> r.recording().dayTime().disaplyInformationFor( r.recording().date() ) );
      configuration.initialiseDoubleColumn( table, COLUMN_TITLE_WEIGHT, 0.15, r -> r.recording().weight(), true );
      configuration.initialiseDoubleColumn( table, COLUMN_TITLE_BODY_FAT, 0.15, r -> r.recording().bodyFat(), true );
      configuration.initialiseDoubleColumn( table, COLUMN_TITLE_LEAN_MASS, 0.15, r -> r.recording().leanMass(), true );
      configuration.initialiseStringColumn( table, COLUMN_TITLE_TYPE, 0.15, r -> r.recording().recordingType().name() );
   }//End Method
   
}//End Class
