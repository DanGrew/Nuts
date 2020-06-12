/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.progress.weight;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import uk.dangrew.kode.javafx.table.base.FriendlyTableView;
import uk.dangrew.nuts.progress.weight.WeightProgress;
import uk.dangrew.nuts.progress.weight.WeightRecording;

/**
 * {@link WeighInTable} provides a {@link TableView} for {@link WeightRecording}s.
 */
public class WeighInTable extends TableView< WeightRecordingRow > 
   implements FriendlyTableView< WeightRecordingRow > 
{

   /**
    * Constructs a new {@link WeighInTable}.
    * @param progress the {@link WeightProgress} to display.
    */
   public WeighInTable( WeightProgress progress ) {
      this.setEditable( true );
      new WeighInTableColumns().populateColumns( this );
      
      List< WeightRecording > copy = new ArrayList<>( progress.records() );
      Collections.reverse( copy );
      copy.forEach( r -> getRows().add( new WeightRecordingRow( r ) ) );
   }//End Constructor
   
   /**
    * Friendly access to the {@link #getItems()}.
    * @return the {@link ObservableList}.
    */
   public ObservableList< WeightRecordingRow > getRows(){
      return getItems();
   }//End Method
   
}//End Class
