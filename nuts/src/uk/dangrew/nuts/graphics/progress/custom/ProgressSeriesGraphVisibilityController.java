package uk.dangrew.nuts.graphics.progress.custom;

import javafx.beans.value.ObservableValue;
import uk.dangrew.nuts.graphics.common.CheckBoxController;
import uk.dangrew.nuts.graphics.common.CheckBoxControllerImpl;
import uk.dangrew.nuts.graphics.graph.custom.GraphSeriesVisibility;
import uk.dangrew.nuts.progress.custom.ProgressSeries;

public class ProgressSeriesGraphVisibilityController extends CheckBoxControllerImpl< ProgressSeries > implements CheckBoxController< ProgressSeries >{

   private final GraphSeriesVisibility controller;
   
   public ProgressSeriesGraphVisibilityController( GraphSeriesVisibility controller ) {
      this.controller = controller;
   }//End Constructor
   
   @Override protected void apply( ObservableValue< ? extends Boolean > property, boolean o, boolean included ) {
      if ( included ) {
         controller.show( conceptFor( property ) );
      } else {
         controller.hide( conceptFor( property ) );
      }
   }//End Method
   
}//End Class
