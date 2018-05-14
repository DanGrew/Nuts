package uk.dangrew.nuts.graphics.graph.custom;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Tooltip;
import uk.dangrew.kode.friendly.javafx.FriendlyTooltip;

public class DataPointTooltipInstaller implements ChangeListener< Node > {

   private final FriendlyTooltip tooltipKit;
   private final Data< Number, Number > source;
   private final Tooltip tooltip;
   
   public DataPointTooltipInstaller( Data< Number, Number > source, Tooltip tooltip ) {
      this( new FriendlyTooltip(), source, tooltip ) ;
   }//End Constructor
   
   DataPointTooltipInstaller( FriendlyTooltip tooltipKit, Data< Number, Number > source, Tooltip tooltip ) {
      this.tooltipKit = tooltipKit;
      this.tooltip = tooltip;
      this.source = source;
      this.source.nodeProperty().addListener( this );
   }//End Constructor
   
   @Override public void changed( ObservableValue< ? extends Node > observable, Node oldValue, Node newValue ) {
      if ( newValue == null ) {
         return;
      }
      tooltipKit.friendly_install( newValue, tooltip );
      source.nodeProperty().removeListener( this );
   }//End Method

}//End Class
