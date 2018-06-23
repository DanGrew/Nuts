/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.settings;

import uk.dangrew.nuts.configuration.NutsSettings;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

class SettingsWriteModel {
   
   private final NutsSettings settings;
   
   SettingsWriteModel( NutsSettings settings ) {
      this.settings = settings;
   }//End Constructor
   
   Boolean isShowingNutritionalUnit( NutritionalUnit unit ) {
      return settings.showingPropertyFor( unit ).get();
   }//End Method
   
}//End Class
