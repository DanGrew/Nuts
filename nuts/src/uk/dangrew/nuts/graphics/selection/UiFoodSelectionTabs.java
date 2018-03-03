package uk.dangrew.nuts.graphics.selection;

import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import uk.dangrew.nuts.apis.tesco.graphics.selection.UiTescoFoodSelectionView;
import uk.dangrew.nuts.store.Database;

public class UiFoodSelectionTabs extends TabPane {

   public UiFoodSelectionTabs( Database database, UiFoodSelectionController controller ) {
      getTabs().add( createTab( "Local", new UiFoodSelectionView( database, controller ) ) );
      getTabs().add( createTab( "Tesco", new UiTescoFoodSelectionView() ) );
   }//End Constructor
   
   private Tab createTab( String name, Node content ) {
      Tab tab = new Tab( name, content );
      tab.setClosable( false );
      return tab;
   }//End Method
   
}//End Class
