package uk.dangrew.nuts.graphics.day;

import uk.dangrew.kode.friendly.controlsfx.FriendlyAlert;

public class UiConfirmAlert extends FriendlyAlert {

   public UiConfirmAlert() {
      super( AlertType.CONFIRMATION );
      setTitle( "Please Confirm..." );
      setContentText( "Are you sure?" );
   }//End Constructor

}//End Class
