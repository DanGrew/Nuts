package uk.dangrew.nuts.graphics.settings;

import java.util.function.BiFunction;

import uk.dangrew.kode.settings.item.SettingsItem;
import uk.dangrew.kode.settings.item.SettingsItemType;
import uk.dangrew.kode.settings.tree.SettingsController;
import uk.dangrew.nuts.configuration.NutsSettings;

public enum NutsSettingsTreeItemType implements SettingsItemType {

   NutritionalUnitShowing( NutritionalUnitShowingItem::new );
   
   private final BiFunction< SettingsController, NutsSettings, SettingsItem > provider;
   
   private NutsSettingsTreeItemType( BiFunction< SettingsController, NutsSettings, SettingsItem > provider ) {
      this.provider = provider;
   }//End Constructor
   
   public SettingsItem construct( SettingsController controller, NutsSettings settings ) {
      return provider.apply( controller, settings );
   }//End Method
   
}//End Enum
