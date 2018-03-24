package uk.dangrew.nuts.graphics.selection;

import java.util.ArrayList;
import java.util.List;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;

public class FoodSelectionPaneManager implements SelectionPaneManager {
   
   private final UiFoodSelector< FoodPortion > selector;
   private final TileFactory tileFactory;
   private final UiFoodSelectionPane selectionPane;
   
   public FoodSelectionPaneManager( UiFoodSelector< FoodPortion > selector ) {
      this( new FoodTileFactory(), new UiFoodSelectionPane(), selector );
   }//End Constructor
   
   public FoodSelectionPaneManager( UiFoodSelector< FoodPortion > selector, UiFoodSelectionPane selectionPane ) {
      this( new FoodTileFactory(), selectionPane, selector );
   }//End Constructor
   
   FoodSelectionPaneManager( TileFactory tileFactory, UiFoodSelectionPane selectionPane, UiFoodSelector< FoodPortion > selector ) {
      this.tileFactory = tileFactory;
      this.selectionPane = selectionPane;
      this.selector = selector;
   }//End Constructor

   @Override public void layoutTiles( List< Food > foods ) {
      List< UiFoodSelectionTile > tiles = new ArrayList<>();
      foods.forEach( f -> tiles.add( tileFactory.create( f, selector ) ) );
      selectionPane.layoutTiles( tiles );
   }//End Method

   @Override public void setSelected( FoodPortion portion, boolean selected ) {
      UiFoodTile tile = tileFactory.get( portion.food().get() );
      if ( tile == null ) {
         return;
      }
      tile.setSelected( selected );
   }//End Method
   
   @Override public UiFoodSelectionPane selectionPane() {
      return selectionPane;
   }//End Method
   
}//End Class
