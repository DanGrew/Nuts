package uk.dangrew.nuts.graphics.tutorial.architecture.tutorial;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import uk.dangrew.nuts.graphics.tutorial.architecture.tutor.TutorPopOver;

public class TutorialProgressor implements ChangeListener< Boolean >{

   private Runnable callback;
   
   public TutorialProgressor( TutorPopOver popOver ) {
      this( popOver.showingProperty() );
   }//End Constructor
   
   TutorialProgressor( ReadOnlyBooleanProperty readOnlyBooleanProperty ) {
      readOnlyBooleanProperty.addListener( this );
   }//End Constructor
   
   public void oneTimeOnlyCallback( Runnable callback ) {
      this.callback = callback;
   }//End Method
   
   @Override public void changed( ObservableValue< ? extends Boolean > observable, Boolean oldValue, Boolean newValue ) {
      if ( callback == null || newValue ) {
         return;
      }
      
      Runnable temp = callback;
      callback = null;
      temp.run();
   }//End Method

}//End Class
