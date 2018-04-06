package uk.dangrew.nuts.graphics.selection.tiles;

import java.util.HashMap;
import java.util.Map;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.selection.view.UiFoodSelector;

public class FoodTileFactory {
   
   private final Map< Food, UiFoodTile > tiles;
   
   public FoodTileFactory() {
      this.tiles = new HashMap<>();
   }//End Constructor
   
   public UiFoodTile create( Food food, UiFoodSelector< FoodPortion > controller ) {
      UiFoodTile tile = tiles.get( food );
      if ( tile == null ) {
         tile = new UiFoodTile( new FoodPortion( food, 100 ), controller );
         tiles.put( food, tile );
      }
      return tile;
   }//End Method

   public UiFoodTile get( Food food ) {
      return tiles.get( food );
   }//End Method

}//End Class
