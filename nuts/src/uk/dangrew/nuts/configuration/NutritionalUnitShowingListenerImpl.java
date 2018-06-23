package uk.dangrew.nuts.configuration;

import java.util.function.Consumer;

import uk.dangrew.nuts.nutrients.NutritionalUnit;

public class NutritionalUnitShowingListenerImpl implements NutritionalUnitShowingListener {

   private final Consumer< NutritionalUnit > show;
   private final Consumer< NutritionalUnit > hide;
   
   public NutritionalUnitShowingListenerImpl( Consumer< NutritionalUnit > show, Consumer< NutritionalUnit > hide ) {
      this.show = show;
      this.hide = hide;
   }//End Constructor

   @Override public void show( NutritionalUnit unit ) {
      this.show.accept( unit );
   }//End Method

   @Override public void hide( NutritionalUnit unit ) {
      this.hide.accept( unit );
   }//End Method

}//End Class
