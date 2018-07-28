package uk.dangrew.nuts.graphics.table.controls;

import javafx.scene.control.Button;

public interface TableControlSet {

   public void addButtons( TableControls tableControls, double prefButtonWidth );

   public Button getButton( TableControlType type );

}//End Interface

