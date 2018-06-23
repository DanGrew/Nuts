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

class SettingsParseModel {
   
   private final NutsSettings settings;
   
   SettingsParseModel( NutsSettings settings ) {
      this.settings = settings;
   }//End Constructor
   
   void showNutritionalUnit( NutritionalUnit unit, Boolean show ) {
      this.settings.showingPropertyFor( unit ).set( show );
   }//End Method
   
}//End Class
