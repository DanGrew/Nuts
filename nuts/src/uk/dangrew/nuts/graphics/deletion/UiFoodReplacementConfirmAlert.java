package uk.dangrew.nuts.graphics.deletion;

import javafx.scene.control.ButtonType;
import uk.dangrew.kode.friendly.controlsfx.FriendlyAlert;

public class UiFoodReplacementConfirmAlert extends FriendlyAlert {

   public static final ButtonType DELETE = new ButtonType( "Delete" );
   public static final ButtonType REPLACE = new ButtonType( "Replace" );
   
   public UiFoodReplacementConfirmAlert() {
      super( AlertType.INFORMATION );
      getButtonTypes().clear();
      getButtonTypes().addAll( REPLACE, DELETE, ButtonType.CANCEL );
      setTitle( "Food Replacement" );
      setHeaderText( "The chosen food is used by other components of the database." );
      setContentText( "Would you like to replace this food?" );
   }//End Constructor

}//End Class
