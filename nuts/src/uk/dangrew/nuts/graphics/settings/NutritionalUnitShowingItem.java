package uk.dangrew.nuts.graphics.settings;

import uk.dangrew.kode.settings.item.ScrollableSettingsItem;
import uk.dangrew.kode.settings.item.SimpleSettingsContentTitle;
import uk.dangrew.kode.settings.tree.SettingsController;
import uk.dangrew.nuts.configuration.NutsSettings;

public class NutritionalUnitShowingItem extends ScrollableSettingsItem {

   public NutritionalUnitShowingItem( SettingsController controller, NutsSettings settings ) {
      super( 
               NutsSettingsTreeItemType.NutritionalUnitShowing,
               "Nutritional Units", 
               new SimpleSettingsContentTitle( 
                        "Nutritional Units", 
                        "Turn nutritional units on/off based on your goals and interests. This "
                        + "will show/hide the relevant columns throughout the tables within the "
                        + "system." 
               ),
               controller,
               new NutritionalUnitShowingSettingsPane( settings )
      );
   }//End Constructor

}//End Class
