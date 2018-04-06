package uk.dangrew.nuts.graphics.selection.model;

import java.util.ArrayList;
import java.util.List;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.selection.tiles.FoodTileFactory;
import uk.dangrew.nuts.graphics.selection.tiles.UiFoodTile;
import uk.dangrew.nuts.graphics.selection.view.UiFoodSelectionPane;
import uk.dangrew.nuts.graphics.selection.view.UiFoodSelectionTile;
import uk.dangrew.nuts.graphics.selection.view.UiFoodSelector;

public class FoodSelectionPaneManager {
   
   private final UiFoodSelector< FoodPortion > selector;
   private final FoodTileFactory tileFactory;
   private final UiFoodSelectionPane selectionPane;
   
   public FoodSelectionPaneManager( UiFoodSelector< FoodPortion > selector ) {
      this( new FoodTileFactory(), new UiFoodSelectionPane(), selector );
   }//End Constructor
   
   public FoodSelectionPaneManager( UiFoodSelector< FoodPortion > selector, UiFoodSelectionPane selectionPane ) {
      this( new FoodTileFactory(), selectionPane, selector );
   }//End Constructor
   
   FoodSelectionPaneManager( FoodTileFactory tileFactory, UiFoodSelectionPane selectionPane, UiFoodSelector< FoodPortion > selector ) {
      this.tileFactory = tileFactory;
      this.selectionPane = selectionPane;
      this.selector = selector;
   }//End Constructor

   public void layoutTiles( List< Food > foods ) {
      List< UiFoodSelectionTile > tiles = new ArrayList<>();
      foods.forEach( f -> tiles.add( tileFactory.create( f, selector ) ) );
      selectionPane.layoutTiles( tiles );
   }//End Method

   public void setSelected( FoodPortion portion, boolean selected ) {
      UiFoodTile tile = tileFactory.get( portion.food().get() );
      if ( tile == null ) {
         return;
      }
      tile.setSelected( selected );
   }//End Method
   
   public UiFoodSelectionPane selectionPane() {
      return selectionPane;
   }//End Method
   
}//End Class
