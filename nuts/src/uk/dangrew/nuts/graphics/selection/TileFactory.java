package uk.dangrew.nuts.graphics.selection;

import uk.dangrew.nuts.food.Food;

public interface TileFactory {

   public UiFoodTile create( Food food, UiFoodSelector controller );

   public UiFoodTile get( Food food );
   
}//End Interface

