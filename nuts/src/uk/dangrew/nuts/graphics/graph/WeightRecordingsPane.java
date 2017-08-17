/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.graph;

import javafx.scene.layout.BorderPane;
import uk.dangrew.nuts.graphics.progress.WeighInTable;
import uk.dangrew.nuts.progress.WeightProgress;

/**
 * {@link WeightRecordingsPane} provides a wrapper for the {@link CombinedWeightRecordingGraph} and
 * the {@link WeighInTable}.
 */
public class WeightRecordingsPane extends BorderPane {

   /**
    * Constructs a new {@link WeightRecordingsPane}.
    * @param progress the {@link WeightProgress}.
    */
   public WeightRecordingsPane( WeightProgress progress ) {
      CombinedWeightRecordingGraph graph = new CombinedWeightRecordingGraph( progress );
      
      setCenter( graph );
      setTop( new WeighInTable( progress ) );
   }//End Constructor
}//End Class
