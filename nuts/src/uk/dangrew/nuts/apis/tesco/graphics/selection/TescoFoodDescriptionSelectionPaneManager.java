package uk.dangrew.nuts.apis.tesco.graphics.selection;

import java.util.ArrayList;
import java.util.List;

import uk.dangrew.nuts.apis.tesco.model.nuts.TescoFoodDescription;
import uk.dangrew.nuts.graphics.selection.view.UiFoodSelectionPane;
import uk.dangrew.nuts.graphics.selection.view.UiFoodSelectionTile;
import uk.dangrew.nuts.graphics.selection.view.UiFoodSelector;
import uk.dangrew.nuts.graphics.system.ImageLoaderService;

public class TescoFoodDescriptionSelectionPaneManager {

   private final ImageLoaderService imageLoader;
   private final UiFoodSelector< TescoFoodDescription > selector;
   private final TescoFoodTileFactory tileFactory;
   private final UiFoodSelectionPane selectionPane;
   
   public TescoFoodDescriptionSelectionPaneManager( UiFoodSelector< TescoFoodDescription > selector ) {
      this.selector = selector;
      this.tileFactory = new TescoFoodTileFactory();
      this.selectionPane = new UiFoodSelectionPane();
      this.imageLoader = new ImageLoaderService();
   }//End Constructor
   
   public void layoutTiles( List< TescoFoodDescription > foods ) {
      List< UiFoodSelectionTile > tiles = new ArrayList<>();
      imageLoader.cancelInProgress();
      foods.forEach( f -> {
         UiTescoFoodTile tile = tileFactory.create( f, selector ); 
         tile.loadImage( imageLoader );
         tiles.add( tile );  
      } );
      selectionPane.layoutTiles( tiles );
   }//End Method

   public void setSelected( TescoFoodDescription portion, boolean selected ) {
      UiTescoFoodTile tile = tileFactory.get( portion );
      if ( tile == null ) {
         return;
      }
      tile.setSelected( selected );
   }//End Method

   public UiFoodSelectionPane selectionPane() {
      return selectionPane;
   }//End Method

}//End Class
