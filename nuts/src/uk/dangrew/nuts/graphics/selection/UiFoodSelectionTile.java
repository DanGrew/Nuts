package uk.dangrew.nuts.graphics.selection;

import javafx.scene.layout.GridPane;

//awful lack of use of interfaces forces an abstract extension - thanks jfx!
public abstract class UiFoodSelectionTile extends GridPane {

   public abstract void setSelected( boolean selected );
   
}//End Interface

