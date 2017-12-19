package uk.dangrew.nuts.cycle;

import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum CycleApproach {

   LowThenHigh,
   HighThenLow
   ;
   
   public static ObservableList< CycleApproach > observableOptions(){
      return FXCollections.observableArrayList( Arrays.asList( CycleApproach.values() ) );
   }//End Method
   
}//End Enum
