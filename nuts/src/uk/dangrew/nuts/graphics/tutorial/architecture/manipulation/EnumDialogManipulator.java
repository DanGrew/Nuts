package uk.dangrew.nuts.graphics.tutorial.architecture.manipulation;

import javafx.event.Event;
import uk.dangrew.nuts.graphics.common.UiEnumTypeSelectionDialog;

public class EnumDialogManipulator< EnumTypeT extends Enum< ? > > {

   private final UiEnumTypeSelectionDialog< EnumTypeT > dialog;
   
   public EnumDialogManipulator( UiEnumTypeSelectionDialog< EnumTypeT > dialog ) {
      this.dialog = dialog;
   }//End Constructor
   
   public EnumDialogManipulator< EnumTypeT > disableInput(){
      dialog.getDialogPane().setMouseTransparent( true );
      dialog.getDialogPane().addEventFilter( Event.ANY, Event::consume );
      return this;
   }//End Method
   
   public EnumDialogManipulator< EnumTypeT > select( EnumTypeT selection ){
      dialog.setSelectedItem( selection );
      dialog.setResult( selection );
      return this;
   }//End Method
   
   public EnumDialogManipulator< EnumTypeT > close(){
      dialog.close();
      return this;
   }//End Method
   
   public EnumDialogManipulator< EnumTypeT > selectAndClose( EnumTypeT selection ){
      select( selection );
      close();
      return this;
   }//End Method
   
}//End Class
