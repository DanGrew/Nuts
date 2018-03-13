package uk.dangrew.nuts.apis.tesco.model.api;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class GuidelineDailyAmountOfEnergy extends GuidelineDailyAmount {

   private final ObjectProperty< String > energyInKj;
   
   public GuidelineDailyAmountOfEnergy() {
      this.energyInKj = new SimpleObjectProperty<>();
   }//End Constructor
   
   public ObjectProperty< String > energyInKcal(){
      return amount();
   }//End Method
   
   public ObjectProperty< String > energyInKj(){
      return energyInKj;
   }//End Method
   
}//End Class
