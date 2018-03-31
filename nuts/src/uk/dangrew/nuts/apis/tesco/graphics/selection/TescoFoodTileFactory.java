package uk.dangrew.nuts.apis.tesco.graphics.selection;

import java.util.HashMap;
import java.util.Map;

import uk.dangrew.nuts.apis.tesco.model.nuts.TescoFoodDescription;
import uk.dangrew.nuts.apis.tesco.model.nuts.TescoFoodReference;
import uk.dangrew.nuts.graphics.selection.UiFoodSelector;

public class TescoFoodTileFactory {
   
   private final Map< TescoFoodReference, UiTescoFoodTile > tiles;
   
   public TescoFoodTileFactory() {
      this.tiles = new HashMap<>();
   }//End Constructor

   public UiTescoFoodTile create( TescoFoodDescription food, UiFoodSelector< TescoFoodDescription > controller ) {
      UiTescoFoodTile tile = tiles.get( food );
      if ( tile == null ) {
         tile = new UiTescoFoodTile( food, controller );
         tiles.put( food, tile );
      }
      return tile;
   }//End Method

   public UiTescoFoodTile get( TescoFoodReference food ) {
      return tiles.get( food );
   }//End Method

}//End Class
