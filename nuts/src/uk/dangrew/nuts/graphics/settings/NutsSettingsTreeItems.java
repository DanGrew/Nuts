package uk.dangrew.nuts.graphics.settings;

import uk.dangrew.kode.settings.item.SettingsItem;
import uk.dangrew.kode.settings.tree.SettingsController;
import uk.dangrew.kode.settings.tree.SettingsTreeItems;
import uk.dangrew.nuts.configuration.NutsSettings;

public class NutsSettingsTreeItems implements SettingsTreeItems {

   private final NutsSettings settings;
   
   public NutsSettingsTreeItems( NutsSettings settings ) {
      this.settings = settings;
   }//End Constructor
   
   @Override public void build( SettingsController controller, SettingsItem root ) {
      NutritionalUnitShowingItem unitShowingItem = new NutritionalUnitShowingItem( controller, settings );
      root.appendChild( unitShowingItem );
      unitShowingItem.select();
   }//End Method

}//End Class
