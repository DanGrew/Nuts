package uk.dangrew.nuts.graphics.selection;

import java.util.HashMap;
import java.util.Map;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;

public class FoodTileFactory implements TileFactory {
   
   private final Map< Food, UiFoodTile > tiles;
   
   public FoodTileFactory() {
      this.tiles = new HashMap<>();
   }//End Constructor
   
   @Override public UiFoodTile create( Food food, UiFoodSelector controller ) {
      UiFoodTile tile = tiles.get( food );
      if ( tile == null ) {
         tile = new UiFoodTile( new FoodPortion( food, 100 ), controller );
         tiles.put( food, tile );
      }
      return tile;
   }//End Method

   @Override public UiFoodTile get( Food food ) {
      return tiles.get( food );
   }//End Method

}//End Class
