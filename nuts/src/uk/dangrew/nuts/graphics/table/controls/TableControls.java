package uk.dangrew.nuts.graphics.table.controls;

import java.util.LinkedHashSet;
import java.util.Set;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class TableControls extends VBox {

   public static final double BUTTON_WIDTH = 40.0;
   public static final double INSETS = 4.0;
   
   private final Set< TableControlSet > controlSets;
   
   public TableControls( TableControlSet... controlSets ) {
      this.controlSets = new LinkedHashSet<>();
      this.setAlignment( Pos.CENTER );
      this.setPadding( new Insets( INSETS ) );
      
      for ( TableControlSet controlSet : controlSets ) {
         controlSet.addButtons( this, BUTTON_WIDTH );
         this.controlSets.add( controlSet );
      }
   }//End Constructor
   
   public Button getButton( TableControlType type ) {
      for ( TableControlSet set : controlSets ) {
         if ( set.getButton( type ) != null ) {
            return set.getButton( type );
         }
      }
      return null;
   }//End Method
   
}//End Class
