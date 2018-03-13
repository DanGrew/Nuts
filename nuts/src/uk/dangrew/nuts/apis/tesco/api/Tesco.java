package uk.dangrew.nuts.apis.tesco.api;

import java.util.List;

import uk.dangrew.nuts.apis.tesco.database.TescoDatabase;
import uk.dangrew.nuts.apis.tesco.model.nuts.TescoFoodDescription;

public class Tesco {

   private final TescoApiController controller;
   
   public Tesco() {
      this( new TescoApiController( new TescoDatabase() ) );
   }//End Constructor
   
   Tesco( TescoApiController controller ) {
      this.controller = controller;
   }//End Constructor

   public List< TescoFoodDescription > search( String criteria ) {
      return controller.search( criteria );
   }//End Method

   public void downloadProductDetail( TescoFoodDescription description ) {
      controller.downloadProductDetail( description );
   }//End Method

}//End Class
