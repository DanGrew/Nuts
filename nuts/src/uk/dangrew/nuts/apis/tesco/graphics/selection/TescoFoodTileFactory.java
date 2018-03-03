package uk.dangrew.nuts.apis.tesco.graphics.selection;

import java.util.HashMap;
import java.util.Map;

import uk.dangrew.nuts.apis.tesco.item.TescoFoodItem;

public class TescoFoodTileFactory {
   
   private final Map< TescoFoodItem, UiTescoFoodTile > tiles;
   
   public TescoFoodTileFactory() {
      this.tiles = new HashMap<>();
   }//End Constructor

   public UiTescoFoodTile create( TescoFoodItem food, UiTescoFoodSelector controller ) {
      UiTescoFoodTile tile = tiles.get( food );
      if ( tile == null ) {
         tile = new UiTescoFoodTile( food, controller );
         tiles.put( food, tile );
      }
      return tile;
   }//End Method

   public UiTescoFoodTile get( TescoFoodItem food ) {
      return tiles.get( food );
   }//End Method

}//End Class
