package uk.dangrew.nuts.graphics.common;

public class UiWindowControlsActionsImpl implements UiWindowControlsActions {

   private final Runnable applyFunction;
   private final Runnable cancelFunction;
   
   public UiWindowControlsActionsImpl( 
            Runnable applyFunction,
            Runnable cancelFunction 
   ){
      this.applyFunction = applyFunction;
      this.cancelFunction = cancelFunction;
   }//End Constructor
   
   @Override public void apply() {
      applyFunction.run();
   }//End Method

   @Override public void cancel() {
      cancelFunction.run();
   }//End Method

}//End Interface

