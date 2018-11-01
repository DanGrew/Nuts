package uk.dangrew.nuts.graphics.deletion;

import java.util.Collection;
import java.util.StringJoiner;

import javafx.scene.control.ButtonType;
import uk.dangrew.kode.concept.Concept;
import uk.dangrew.kode.friendly.controlsfx.FriendlyAlert;

public class UiFoodReplacementConfirmAlert extends FriendlyAlert {

   public static final ButtonType DELETE = new ButtonType( "Delete" );
   public static final ButtonType REPLACE = new ButtonType( "Replace" );
   
   private String usageInformation;
   
   public UiFoodReplacementConfirmAlert() {
      super( AlertType.INFORMATION );
      getButtonTypes().clear();
      getButtonTypes().addAll( REPLACE, DELETE, ButtonType.CANCEL );
      setTitle( "Food Replacement" );
      updateHeaderText();
      setContentText( "Would you like to replace this food?" );
   }//End Constructor
   
   private void updateHeaderText(){
      setHeaderText( 
               "The chosen food is used by other components of the database:\n"
               + usageInformation
      );
   }//End Method
   
   public void setUsageInformation( Collection< Concept > usages ){
      if ( usages == null || usages.isEmpty() ) {
         usageInformation = "Unknown";
      } else {
         StringJoiner joiner = new StringJoiner( "," );
         usages.forEach( c -> joiner.add( c.properties().nameProperty().get() ) );
         usageInformation = joiner.toString();
      }
      updateHeaderText();
   }//End Method

}//End Class
