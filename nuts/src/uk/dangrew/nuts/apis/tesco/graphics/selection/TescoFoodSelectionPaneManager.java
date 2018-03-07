package uk.dangrew.nuts.apis.tesco.graphics.selection;

import java.util.ArrayList;
import java.util.List;

import uk.dangrew.nuts.apis.tesco.item.TescoFoodDescription;
import uk.dangrew.nuts.graphics.selection.UiFoodSelectionPane;
import uk.dangrew.nuts.graphics.selection.UiFoodSelectionTile;
import uk.dangrew.nuts.graphics.system.ImageLoaderService;

public class TescoFoodSelectionPaneManager implements TescoSelectionPaneManager {

//   private static final ImageLoaderService imageLoader = new ImageLoaderService();
   private final UiTescoFoodSelector selector;
   private final TescoFoodTileFactory tileFactory;
   private final UiFoodSelectionPane selectionPane;
   
   public TescoFoodSelectionPaneManager( UiTescoFoodSelector selector ) {
      this.selector = selector;
      this.tileFactory = new TescoFoodTileFactory();
      this.selectionPane = new UiFoodSelectionPane();
   }//End Constructor
   
   @Override public void layoutTiles( List< TescoFoodDescription > foods ) {
      List< UiFoodSelectionTile > tiles = new ArrayList<>();
//      imageLoader.cancelInProgress();
      foods.forEach( f -> tiles.add( tileFactory.create( f, selector, null ) ) );
      selectionPane.layoutTiles( tiles );
   }//End Method

   @Override public void setSelected( TescoFoodDescription portion, boolean selected ) {
      UiTescoFoodTile tile = tileFactory.get( portion );
      if ( tile == null ) {
         return;
      }
      tile.setSelected( selected );
   }//End Method

   @Override public UiFoodSelectionPane selectionPane() {
      return selectionPane;
   }//End Method

}//End Class
