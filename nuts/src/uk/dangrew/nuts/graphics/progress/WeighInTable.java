/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.progress;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import uk.dangrew.nuts.graphics.FriendlyTableView;
import uk.dangrew.nuts.progress.WeightProgress;

/**
 * {@link WeighInTable} provides a {@link TableView} for {@link uk.dangrew.nuts.progress.WeightRecording}s.
 */
public class WeighInTable extends TableView< WeighInRecordRow > 
   implements FriendlyTableView< WeighInRecordRow > 
{

   /**
    * Constructs a new {@link WeighInTable}.
    * @param progress the {@link WeightProgress} to display.
    */
   public WeighInTable( WeightProgress progress ) {
      this.setEditable( true );
      new WeighInTableColumns().populateColumns( this );
      progress.records().forEach( r -> getRows().add( new WeighInRecordRow( r ) ) );
   }//End Constructor
   
   /**
    * Friendly access to the {@link #getItems()}.
    * @return the {@link ObservableList}.
    */
   public ObservableList< WeighInRecordRow > getRows(){
      return getItems();
   }//End Method
   
}//End Class
