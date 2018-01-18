package uk.dangrew.nuts.graphics.cycle;

import uk.dangrew.kode.friendly.controlsfx.FriendlyAlert;

public class UiCycleDialogs {

   static class NoBaseGoalDialog extends FriendlyAlert {
      
      public NoBaseGoalDialog() {
         super( AlertType.INFORMATION );
         setTitle( "Goal creation failed." );
         setContentText( "Cannot create Goal - there is no base Goal defined." );
      }//End Constructor
      
   }//End Class
   
   static class NoFocusDialog extends FriendlyAlert {
      
      public NoFocusDialog() {
         super( AlertType.INFORMATION );
         setTitle( "Goal creation failed." );
         setContentText( "Cannot create Goal - a cycle has not been selected." );
      }//End Constructor
      
   }//End Class

   private final NoBaseGoalDialog noBaseGoalDialog;
   private final NoFocusDialog noFocusDialog;
   
   public UiCycleDialogs() {
      this( new NoBaseGoalDialog(), new NoFocusDialog() );
   }//End Class
   
   UiCycleDialogs( NoBaseGoalDialog noBaseGoalDialog, NoFocusDialog noFocusDialog ) {
      this.noBaseGoalDialog = noBaseGoalDialog;
      this.noFocusDialog = noFocusDialog;
   }//End Constructor

   public void showNoBaseGoalDialog() {
      noBaseGoalDialog.friendly_showAndWait();
   }//End Method
   
   public void showNoFocusDialog() {
      noFocusDialog.friendly_showAndWait();
   }//End Method
   
}//End Class
