package uk.dangrew.nuts.configuration;

import java.util.LinkedHashSet;
import java.util.Set;

import uk.dangrew.nuts.nutrients.NutritionalUnit;

public class NutsSettingsRegistrations {

   private final Set< NutritionalUnitShowingListener > showingListeners;
   
   public NutsSettingsRegistrations( NutsSettings settings ) {
      this.showingListeners = new LinkedHashSet<>();
      
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         settings.showingPropertyFor( unit ).addListener( ( s, o, n ) -> notifyShowingListeners( unit, n ) );
      }
   }//End Constructor
   
   private void notifyShowingListeners( NutritionalUnit unit, boolean showing ){
      if ( showing ) {
         showingListeners.forEach( l -> l.show( unit ) );
      } else {
         showingListeners.forEach( l -> l.hide( unit ) );
      }
   }//End Method

   public void registerForUnitShowing( NutritionalUnitShowingListener showingListener ) {
      this.showingListeners.add( showingListener );
   }//End Method

}//End Class
