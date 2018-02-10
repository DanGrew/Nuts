package uk.dangrew.nuts.graphics.deletion;

import uk.dangrew.kode.friendly.controlsfx.FriendlyAlert;

public class UiFoodDeletionConfirmAlert extends FriendlyAlert {

   public UiFoodDeletionConfirmAlert() {
      super( AlertType.CONFIRMATION );
      setTitle( "Food Deletion" );
      setHeaderText( "Deleting a food will remove it from the database." );
      setContentText( "Are you sure?" );
   }//End Constructor

}//End Class
